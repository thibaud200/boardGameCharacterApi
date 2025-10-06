package com.boardgamecharacterapi.config;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.engine.jdbc.env.spi.NameQualifierSupport;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.jdbc.*;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super(DatabaseVersion.make(3, 30));
    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes(typeContributions, serviceRegistry);

        // 🧩 Équivalents modernes à registerColumnType(...)
        typeContributions.getTypeConfiguration().getJdbcTypeRegistry().addDescriptor(new IntegerJdbcType());
        typeContributions.getTypeConfiguration().getJdbcTypeRegistry().addDescriptor(new VarcharJdbcType());
        typeContributions.getTypeConfiguration().getJdbcTypeRegistry().addDescriptor(new FloatJdbcType());
        typeContributions.getTypeConfiguration().getJdbcTypeRegistry().addDescriptor(new DoubleJdbcType());
        typeContributions.getTypeConfiguration().getJdbcTypeRegistry().addDescriptor(new BooleanJdbcType());
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
        };
    }

    public boolean supportsIdentityColumns() {
        return true;
    }

    public boolean hasDataTypeInIdentityColumn() {
        return false;
    }

    public String getIdentityColumnString(int type) {
        return "integer";
    }

    public boolean supportsLimit() {
        return true;
    }

    public String getLimitString(String query, boolean hasOffset) {
        return query + (hasOffset ? " limit ? offset ?" : " limit ?");
    }

    @Override
    public NameQualifierSupport getNameQualifierSupport() {
        return NameQualifierSupport.NONE;
    }
}
