package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDropTableStep<T> extends SqlExecutor {
    private SqlTable<T> table;

    public SqlDropTableStep(Connection connection, SqlTable<T> table) {
        super(connection);
        this.table = table;
    }

    @Override
    protected String getQuery() {
        return "DROP TABLE " + table.getTableName();
    }
}
