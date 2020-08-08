package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDeleteQuery<T> {
    private Connection connection;
    private SqlTable<T> table;
    private SqlWhereCondition<SqlDeleteQuery<T>> whereCondition;

    public SqlDeleteQuery(Connection connection, SqlTable<T> table) {
        this.connection = connection;
        this.table = table;
    }


    public SqlWhereCondition<SqlDeleteQuery<T>> where() {
        if (whereCondition == null) {
            whereCondition = new SqlWhereCondition<>(this);
        }
        return whereCondition;
    }

    public SqlDeleteQuery<T> where(String condition) {
        if (whereCondition == null) {
            whereCondition = new SqlWhereCondition<>(this, condition);
        } else {
            whereCondition.custom(condition);
        }
        return this;
    }

    protected String getQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        sb.append(table.getTableName());
        if (whereCondition != null) {
            sb.append(" WHERE ").append(whereCondition.getQuery());
        }
        return sb.toString();
    }

    public int execute() {
        try (Statement st = connection.createStatement()) {
            st.execute(getQuery());
            return st.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
