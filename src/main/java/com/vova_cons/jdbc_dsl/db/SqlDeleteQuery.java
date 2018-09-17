package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlTable;

import java.sql.Connection;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDeleteQuery<T> extends SqlExecutor {
    private SqlTable<T> table;
    private SqlWhereCondition<SqlDeleteQuery<T>> whereCondition;

    public SqlDeleteQuery(Connection connection, SqlTable<T> table) {
        super(connection);
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

    @Override
    protected String getQuery() {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        sb.append(table.getTableName());
        if (whereCondition != null) {
            sb.append(" WHERE ").append(whereCondition.getQuery());
        }
        return sb.toString();
    }
}
