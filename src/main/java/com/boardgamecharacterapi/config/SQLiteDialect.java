package com.boardgamecharacterapi.config;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.engine.jdbc.env.spi.NameQualifierSupport;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ast.SqlAstTranslator;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.tree.select.SelectStatement;
import org.hibernate.sql.model.ast.TableMutation;
import org.hibernate.sql.exec.spi.JdbcOperation;
import org.hibernate.type.descriptor.jdbc.*;

/**
 * ✅ Custom SQLite Dialect compatible Hibernate 6.6.29.Final
 */
public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super(DatabaseVersion.make(3, 36));
    }

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
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl() {
            @Override
            public boolean supportsIdentityColumns() {
                return true;
            }

            @Override
            public String getIdentitySelectString(String table, String column, int type) {
                return "select last_insert_rowid()";
            }

            @Override
            public String getIdentityColumnString(int type) {
                return "integer";
            }
        };
    }

    @Override
    public SqlAstTranslatorFactory getSqlAstTranslatorFactory() {
        return new SqlAstTranslatorFactory() {
            @Override
            public SqlAstTranslator<JdbcOperation> buildSelectTranslator(
                    SessionFactoryImplementor sessionFactory,
                    SelectStatement statement
            ) {
                return new SimpleTranslator(sessionFactory);
            }

            @Override
            public SqlAstTranslator<JdbcOperation> buildModelMutationTranslator(
                    TableMutation<JdbcOperation> mutation,
                    SessionFactoryImplementor sessionFactory
            ) {
                return new SimpleTranslator(sessionFactory);
            }
        };
    }

    /**
     * Simplified translator stub (SQLite n’a pas besoin de custom SQL AST translator)
     */
    private static class SimpleTranslator implements SqlAstTranslator<JdbcOperation> {
        private final SessionFactoryImplementor sessionFactory;

        public SimpleTranslator(SessionFactoryImplementor sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

        @Override
        public SessionFactoryImplementor getSessionFactory() {
            return sessionFactory;
        }

        @Override
        public JdbcOperation translate() {
            // Hibernate gère déjà la traduction SQL pour SQLite
            return null;
        }
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String query, boolean hasOffset) {
        return query + (hasOffset ? " limit ? offset ?" : " limit ?");
    }

    @Override
    public NameQualifierSupport getNameQualifierSupport() {
        return NameQualifierSupport.NONE;
    }
}
