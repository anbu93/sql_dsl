package com.vova_cons.jdbc_dsl.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by anbu on 17.09.18.
 **/
public abstract class SqlExecutor {
    private Connection connection;

    public SqlExecutor(Connection connection) {
        this.connection = connection;
    }

    public boolean execute() {
        try (Statement st = connection.createStatement()) {
            return st.execute(getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected abstract String getQuery();
}
