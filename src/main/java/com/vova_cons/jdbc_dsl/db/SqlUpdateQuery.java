package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlColumn;
import com.vova_cons.jdbc_dsl.dsl.SqlDsl;
import com.vova_cons.jdbc_dsl.dsl.SqlTable;
import com.vova_cons.jdbc_dsl.dsl.StringTemplate;
import com.vova_cons.jdbc_dsl.tool.StringBuilderAppender;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by anbu on 08.08.20.
 * UPDATE table_name
 * SET column1 = value1, column2 = value2, ...
 * WHERE condition;
 **/
public class SqlUpdateQuery<T extends SqlUpdateConditionable> {
    private final Connection connection;
    private final SqlTable<T> table;

    public SqlUpdateQuery(Connection connection, SqlTable<T> table) {
        this.connection = connection;
        this.table = table;
    }

    public int execute(T object) {
        try (Statement st = connection.createStatement()) {
            StringTemplate template = new StringTemplate(getQuery(object));
            String preparedQuery = table.getDemarshaller().demarshall(template, object);
            st.execute(preparedQuery);
            return st.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    protected String getQuery(T object) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(table.getTableName());
        sb.append(" SET ");
        //set values here
        StringBuilderAppender.join(sb, table.getColumns(), this::stringifyColumn, ", ");
        sb.append(" WHERE ").append(object.getConditionColumnName()).append(" = ");
        if (object.isConditionColumnTextType()) {
            sb.append("'{").append(object.getConditionColumnName()).append("}'");
        } else {
            sb.append("{").append(object.getConditionColumnName()).append("}");
        }
        return sb.toString();
    }

    private String stringifyColumn(SqlColumn column) {
        if ((column.getFlags() & SqlDsl.AUTO_INCREMENT) > 0) {
            return null;
        }
        if (column.getType().startsWith("VARCHAR") || column.getType().startsWith("TEXT")) {
            return column.getName() + " = '{" + column.getName() + "}'";
        }
        return column.getName() + " = {" + column.getName() + "}";
    }
}
