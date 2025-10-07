package com.boardgamecharacterapi.config;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.spi.StandardSqlAstTranslatorFactory;
import org.hibernate.tool.schema.internal.StandardTableExporter;
import org.hibernate.tool.schema.spi.Exporter;
import org.hibernate.type.descriptor.jdbc.BooleanJdbcType;
import org.hibernate.type.descriptor.jdbc.DoubleJdbcType;
import org.hibernate.type.descriptor.jdbc.FloatJdbcType;
import org.hibernate.type.descriptor.jdbc.IntegerJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom SQLite Dialect — Hibernate 6.6.x
 * Version minimale et compilable.
 */
public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super(DatabaseVersion.make(3, 36));
    }

    // Types JDBC de base
    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);
        var registry = typeContributions.getTypeConfiguration().getJdbcTypeRegistry();
        registry.addDescriptor(new IntegerJdbcType());
        registry.addDescriptor(new VarcharJdbcType());
        registry.addDescriptor(new FloatJdbcType());
        registry.addDescriptor(new DoubleJdbcType());
        registry.addDescriptor(new BooleanJdbcType());
    }

    @Override
    public SqlAstTranslatorFactory getSqlAstTranslatorFactory() {
        // Utilise la fabrique SQL AST standard de Hibernate
        return new StandardSqlAstTranslatorFactory();
    }

    // Auto-increment / identity
    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl() {
            @Override
            public boolean supportsIdentityColumns() { return true; }

            @Override
            public String getIdentitySelectString(String table, String column, int type) {
                return "select last_insert_rowid()";
            }

            @Override
            public String getIdentityColumnString(int type) {
                // En SQLite, INTEGER PRIMARY KEY -> rowid
                return "integer";
            }
        };
    }

    // Limitations DDL d’SQLite
    @Override
    public boolean hasAlterTable() { return false; }

    @Override
    public boolean dropConstraints() { return false; }

    @Override
    public String getDropForeignKeyString() {
        throw new UnsupportedOperationException("SQLite does not support dropping foreign keys with ALTER TABLE");
    }

    @Override
    public String getAddForeignKeyConstraintString(
            String constraintName,
            String[] foreignKey,
            String referencedTable,
            String[] primaryKey,
            boolean referencesPrimaryKey
    ) {
        throw new UnsupportedOperationException("SQLite does not support adding foreign keys with ALTER TABLE");
    }

    // Exporters
    @Override
    public Exporter<Table> getTableExporter() {
        return new SQLiteTableExporter(this);
    }

    @Override
    public Exporter<ForeignKey> getForeignKeyExporter() {
        // FKs sont intégrées dans CREATE TABLE ; rien à créer/détruire séparément
        return new Exporter<>() {
            @Override
            public String[] getSqlCreateStrings(ForeignKey exportable, Metadata metadata, SqlStringGenerationContext context) {
                return NO_COMMANDS;
            }

            @Override
            public String[] getSqlDropStrings(ForeignKey exportable, Metadata metadata, SqlStringGenerationContext context) {
                return NO_COMMANDS;
            }
        };
    }

    // ---- Exporter de table qui ajoute les FK dans le CREATE TABLE ----
    static class SQLiteTableExporter extends StandardTableExporter {
        private final Dialect dialect;

        private SQLiteTableExporter(Dialect dialect) {
            super(dialect);
            this.dialect = dialect;
        }

        @Override
        public String[] getSqlCreateStrings(Table table, Metadata metadata, SqlStringGenerationContext context) {
            var createStrings = new ArrayList<String>();
            for (String createString : super.getSqlCreateStrings(table, metadata, context)) {
                createStrings.add(appendForeignKeys(createString, table, context));
            }
            return createStrings.toArray(String[]::new);
        }

        private String appendForeignKeys(String createSql, Table table, SqlStringGenerationContext context) {
            var foreignKeys = table.getForeignKeys().values();
            if (foreignKeys.isEmpty()) {
                return createSql;
            }

            var usableForeignKeys = foreignKeys.stream()
                    .filter(ForeignKey::isCreationEnabled)
                    .filter(ForeignKey::isPhysicalConstraint)
                    .toList();

            if (usableForeignKeys.isEmpty()) {
                return createSql;
            }

            var closingParenIndex = createSql.lastIndexOf(')');
            if (closingParenIndex < 0) {
                return createSql;
            }

            var prefix = new StringBuilder(createSql.substring(0, closingParenIndex).trim());
            var suffix = createSql.substring(closingParenIndex);

            boolean firstClause = true;
            for (ForeignKey fk : usableForeignKeys) {
                var clause = renderForeignKeyClause(fk, context);
                if (clause.isEmpty()) continue;

                // ajoute “, ” si nécessaire
                if (prefix.length() > 0 && prefix.charAt(prefix.length() - 1) != '(') {
                    prefix.append(firstClause ? ", " : ", ");
                } else {
                    prefix.append(' ');
                }
                firstClause = false;

                prefix.append(clause);
            }

            prefix.append(suffix);
            return prefix.toString();
        }

        private String renderForeignKeyClause(ForeignKey fk, SqlStringGenerationContext context) {
            var referencedTable = fk.getReferencedTable();
            if (referencedTable == null) return "";

            var sourceColumns = fk.getColumns();
            if (sourceColumns.isEmpty()) return "";

            var referencedColumns = resolveReferencedColumns(fk);
            if (referencedColumns.isEmpty()) return "";

            var sb = new StringBuilder();

            var name = fk.getName();
            if (name != null && !name.isBlank()) {
                sb.append("constraint ").append(dialect.quote(name)).append(' ');
            }

            sb.append("foreign key (");
            appendColumnList(sb, sourceColumns);
            sb.append(") references ")
                    .append(context.format(referencedTable.getQualifiedTableName()))
                    .append('(');
            appendColumnList(sb, referencedColumns);
            sb.append(')');

            // (Optionnel) On peut ajouter ON DELETE / ON UPDATE ici si nécessaire
            return sb.toString();
        }

        private void appendColumnList(StringBuilder sb, List<Column> columns) {
            for (int i = 0; i < columns.size(); i++) {
                sb.append(columns.get(i).getQuotedName(dialect));
                if (i + 1 < columns.size()) sb.append(", ");
            }
        }

        private List<Column> resolveReferencedColumns(ForeignKey fk) {
            if (fk.isReferenceToPrimaryKey()) {
                PrimaryKey pk = fk.getReferencedTable().getPrimaryKey();
                return pk == null ? List.of() : pk.getColumns();
            }
            return fk.getReferencedColumns();
        }
    }
}
