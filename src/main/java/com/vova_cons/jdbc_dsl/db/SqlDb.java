package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlTable;

import java.sql.*;
import java.util.Properties;

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
            DriverManager.setLoginTimeout(0);
            createConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("not found jdbc driver class: " + JDBC_DRIVER_CLASS);
        }
    }

    public void setTimeout(int seconds) {
        DriverManager.setLoginTimeout(seconds);
    }

    private void createConnection() {
        try {
            Properties info = new Properties();
            info.put("user", user);
            info.put("password", pass);
            info.put("autoReconnect", "true");
            info.put("useUnicode", "yes");
            connection = DriverManager.getConnection(url, info);
        } catch (SQLException e) {
            throw new RuntimeException("can't create connection", e);
        }
    }

    public void recreateConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection timeout, recreate connection");
        }
        createConnection();
    }

    public Connection getConnection() {
        if (connection == null){
            createConnection();
        } else {
            try {
                if (!connection.isValid(0)) {
                    System.out.println("Connection timeout, recreate connection");
                    createConnection();
                }
            } catch (SQLException e) {
                System.out.println("Connection timeout, recreate connection");
                e.printStackTrace();
                createConnection();
            }
            //crutch for check connection
//            try (Statement statement = connection.createStatement()) {
//                statement.executeQuery("select 1");
//            } catch (Exception e) {
//                System.out.println("Connection timeout, recreate connection");
//                createConnection();
//            }
        }
        return connection;
    }

    //region interface
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

    public <T> SqlDbSelectQueryStep<T> selectFrom(SqlTable<T> table) {
        return new SqlDbSelectQueryStep<>(table, getConnection());
    }

    public <T extends SqlUpdateConditionable> SqlUpdateQuery<T> update(SqlTable<T> table) {
        return new SqlUpdateQuery<>(getConnection(), table);
    }

    public <T> SqlDeleteQuery<T> deleteFrom(SqlTable<T> table) {
        return new SqlDeleteQuery<>(connection, table);
    }
    //endregion
}
