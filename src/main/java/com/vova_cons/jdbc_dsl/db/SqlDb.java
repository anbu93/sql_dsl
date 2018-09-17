package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlTable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDb {
    private static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private final String url;
    private final String user;
    private final String pass;
    private Connection connection;

    public SqlDb(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.pass = password;
    }

    public void init() {
        try {
            Class.forName(JDBC_DRIVER_CLASS);
            createConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("not found jdbc driver class: " + JDBC_DRIVER_CLASS);
        }
    }

    private void createConnection() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("can't create connection", e);
        }
    }

    public Connection getConnection() {
        if (connection == null){
            createConnection();
        } else {
            //crutch for check connection
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery("select 1");
            } catch (Exception e) {
                System.out.println("Connection timeout, recreate connection");
                createConnection();
            }
        }
        return connection;
    }

    //region interface
    public <T> SqlDbSelectQueryStep<T> selectFrom(SqlTable<T> table) {
        return new SqlDbSelectQueryStep<>(table, getConnection());
    }

    public <T> boolean createTable(SqlTable<T> table) {
        SqlCreateTableStep<T> createTableStep = new SqlCreateTableStep<>(connection, table);
        return createTableStep.execute();
    }

    public <T> boolean dropTable(SqlTable<T> table) {
        SqlDropTableStep<T> dropTableStep = new SqlDropTableStep<>(connection, table);
        return dropTableStep.execute();
    }

    public <T> SqlInsertQuery<T> insertInto(SqlTable<T> table) {
        return new SqlInsertQuery<>(connection, table);
    }

    public <T> SqlDeleteQuery<T> deleteFrom(SqlTable<T> table) {
        return new SqlDeleteQuery<>(connection, table);
    }
    //endregion
}
