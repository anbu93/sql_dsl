package com.vova_cons.jdbc_dsl.dsl;

import java.util.List;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlTable<T> {
    private final String tableName;
    private final List<SqlColumn> columns;
    private final Marshaller<T> marshaller;
    private final Demarshaller<T> demarshaller;

    SqlTable(String tableName, List<SqlColumn> columns, Marshaller<T> marshaller, Demarshaller<T> demarshaller) {
        this.tableName = tableName;
        this.columns = columns;
        this.marshaller = marshaller;
        this.demarshaller = demarshaller;
    }

    public String getTableName() {
        return tableName;
    }

    public List<SqlColumn> getColumns() {
        return columns;
    }

    public Marshaller<T> getMarshaller() {
        return marshaller;
    }

    public Demarshaller<T> getDemarshaller() {
        return demarshaller;
    }
}
