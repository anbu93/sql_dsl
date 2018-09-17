package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlColumn;
import com.vova_cons.jdbc_dsl.dsl.SqlTable;
import com.vova_cons.jdbc_dsl.tool.StringBuilderAppender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDbSelectQueryStep<T> {
    private final SqlTable<T> table;
    private final Connection connection;
    private SqlWhereCondition<SqlDbSelectQueryStep<T>> whereCondition = null;

    public SqlDbSelectQueryStep(SqlTable<T> table, Connection connection) {
        this.table = table;
        this.connection = connection;
    }

    public List<T> execute() {
        List<T> result = new LinkedList<>();
        try (Statement st = connection.createStatement()) {
            String query = createQuery();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                T obj = table.getMarshaller().marshall(rs);
                result.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public SqlWhereCondition<SqlDbSelectQueryStep<T>> where() {
        if (whereCondition == null) {
            whereCondition = new SqlWhereCondition<>(this);
        }
        return whereCondition;
    }

    public SqlDbSelectQueryStep<T> where(String condition) {
        if (whereCondition == null) {
            whereCondition = new SqlWhereCondition<>(this, condition);
        } else {
            whereCondition.custom(condition);
        }
        return this;
    }


    String createQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb = StringBuilderAppender.join(sb, table.getColumns(), SqlColumn::getName, ", ");
        sb.append(" FROM ").append(table.getTableName());
        if (whereCondition != null) {
            sb.append(" WHERE ").append(whereCondition.getQuery());
        }
        return sb.toString();
    }
}
