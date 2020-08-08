package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlColumn;
import com.vova_cons.jdbc_dsl.dsl.SqlDsl;
import com.vova_cons.jdbc_dsl.dsl.SqlTable;
import com.vova_cons.jdbc_dsl.dsl.StringTemplate;
import com.vova_cons.jdbc_dsl.tool.StringBuilderAppender;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlInsertQuery<T> {
    private final Connection connection;
    private final SqlTable<T> table;

    public SqlInsertQuery(Connection connection, SqlTable<T> table) {
        this.connection = connection;
        this.table = table;
    }

    public int execute(Collection<T> objects) {
        try (Statement st = connection.createStatement()) {
            StringTemplate template = new StringTemplate(getQuery());
            int count = 0;
            for(T obj : objects) {
                StringTemplate objectTemplate = new StringTemplate(template);
                String preparedQuery = table.getDemarshaller().demarshall(objectTemplate, obj);
                st.execute(preparedQuery);
                count += st.getUpdateCount();
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int execute(T... objects) {
        try (Statement st = connection.createStatement()) {
            StringTemplate template = new StringTemplate(getQuery());
            int count = 0;
            for(T obj : objects) {
                StringTemplate objectTemplate = new StringTemplate(template);
                String preparedQuery = table.getDemarshaller().demarshall(objectTemplate, obj);
                st.execute(preparedQuery);
                count += st.getUpdateCount();
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int execute(T object) {
        try (Statement st = connection.createStatement()) {
            StringTemplate template = new StringTemplate(getQuery());
            String preparedQuery = table.getDemarshaller().demarshall(template, object);
            st.execute(preparedQuery);
            return st.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    protected String getQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(table.getTableName());
        sb.append("(");
        StringBuilderAppender.join(sb, table.getColumns(), this::stringifyColumn, ", ");
        sb.append(") VALUES(");
        StringBuilderAppender.join(sb, table.getColumns(), this::stringifyColumnId, ", ");
        sb.append(")");
        return sb.toString();
    }

    private String stringifyColumn(SqlColumn column) {
        if ((column.getFlags() & SqlDsl.AUTO_INCREMENT) > 0) {
            return null;
        }
        return column.getName();
    }

    private String stringifyColumnId(SqlColumn column) {
        if ((column.getFlags() & SqlDsl.AUTO_INCREMENT) > 0) {
            return null;
        }
        if (column.getType().startsWith("VARCHAR") || column.getType().startsWith("TEXT")) {
            return "'{" + column.getName() + "}'";
        }
        return "{" + column.getName() + "}";
    }
}
