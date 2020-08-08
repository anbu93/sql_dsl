package com.vova_cons.jdbc_dsl.dsl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDslCreateTableStep<T> {
    private String tableName;
    private List<SqlColumn> columns = new ArrayList<>();

    public SqlDslCreateTableStep(String tableName) {
        this.tableName = tableName;
    }

    public SqlDslCreateTableStep<T> customField(String name, String type, int flags) {
        columns.add(new SqlColumn(name, type, flags));
        return this;
    }

    public SqlDslCreateTableStep<T> fieldInt(String name) {
        return fieldInt(name, 0);
    }
    public SqlDslCreateTableStep<T> fieldInt(String name, int flags) {
        columns.add(new SqlColumn(name, SqlDsl.INTEGER, flags));
        return this;
    }

    public SqlDslCreateTableStep<T> fieldBool(String name) {
        return fieldBool(name, 0);
    }
    public SqlDslCreateTableStep<T> fieldBool(String name, int flags) {
        columns.add(new SqlColumn(name, SqlDsl.BOOL, flags));
        return this;
    }

    public SqlDslCreateTableStep<T> fieldLong(String name) {
        return fieldLong(name, 0);
    }
    public SqlDslCreateTableStep<T> fieldLong(String name, int flags) {
        columns.add(new SqlColumn(name, SqlDsl.LONG, flags));
        return this;
    }

    public SqlDslCreateTableStep<T> fieldVarchar(int size, String name) {
        return fieldVarchar(size, name, 0);
    }
    public SqlDslCreateTableStep<T> fieldVarchar(int size, String name, int flags) {
        columns.add(new SqlColumn(name, SqlDsl.VARCHAR + "(" + size + ")", flags));
        return this;
    }

    public SqlDslCreateTableStep<T> fieldText(String name) {
        return fieldText(name, 0);
    }
    public SqlDslCreateTableStep<T> fieldText(String name, int flags) {
        columns.add(new SqlColumn(name, SqlDsl.TEXT, flags));
        return this;
    }

    public SqlDslCreateTableStep<T> fieldFloat(String name) {
        return fieldFloat(name, 0);
    }
    public SqlDslCreateTableStep<T> fieldFloat(String name, int flags) {
        columns.add(new SqlColumn(name, SqlDsl.FLOAT, flags));
        return this;
    }

    public SqlDslCreateTableStep<T> fieldDouble(String name) {
        return fieldDouble(name, 0);
    }
    public SqlDslCreateTableStep<T> fieldDouble(String name, int flags) {
        columns.add(new SqlColumn(name, SqlDsl.DOUBLE, flags));
        return this;
    }

    public SqlTable<T> done(Marshaller<T> marshaller, Demarshaller<T> demarshaller) {
        return new SqlTable<>(tableName, columns, marshaller, demarshaller);
    }
}
