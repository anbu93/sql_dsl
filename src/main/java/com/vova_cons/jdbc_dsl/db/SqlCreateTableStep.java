package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlColumn;
import com.vova_cons.jdbc_dsl.dsl.SqlDsl;
import com.vova_cons.jdbc_dsl.dsl.SqlTable;
import com.vova_cons.jdbc_dsl.tool.StringBuilderAppender;

import java.sql.Connection;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlCreateTableStep<T> extends SqlExecutor {
    private SqlTable<T> table;

    public SqlCreateTableStep(Connection connection, SqlTable<T> table) {
        super(connection);
        this.table = table;
    }

    @Override
    protected String getQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(table.getTableName());
        sb.append("(");
        sb = StringBuilderAppender.join(sb, table.getColumns(), this::stringifyColumn, ", ");
        boolean isExistsPrimaryKey = false;
        for(SqlColumn column : table.getColumns()) {
            if ((column.getFlags() & SqlDsl.PRIMARY_KEY) > 0) {
                sb.append(", PRIMARY_KEY(");
                isExistsPrimaryKey = true;
                break;
            }
        }
        if (isExistsPrimaryKey) {
            sb = StringBuilderAppender.join(sb, table.getColumns(), this::stringifyPrimaryKeys, ", ");
            sb.append(")");
        }
        boolean isExistUniqueKey = false;
        for(SqlColumn column : table.getColumns()) {
            if ((column.getFlags() & SqlDsl.UNIQUE) > 0) {
                sb.append(", UNIQUE(");
                isExistUniqueKey = true;
                break;
            }
        }
        if (isExistUniqueKey) {
            sb = StringBuilderAppender.join(sb, table.getColumns(), this::stringifyUniqueKeys, ", ");
            sb.append(")");
        }
        sb.append(")");
        return sb.toString();
    }

    private String stringifyColumn(SqlColumn column) {
        return column.getName() + " " + column.getType() + stringifyFlags(column.getFlags());
    }

    private String stringifyFlags(int flags) {
        StringBuilder sb = new StringBuilder();
        if ((flags & SqlDsl.NOT_NULL) > 0) {
            sb.append(" NOT NULL");
        }
        if ((flags & SqlDsl.NULL) > 0) {
            sb.append(" NULL");
        }
        if ((flags & SqlDsl.AUTO_INCREMENT) > 0) {
            sb.append(" AUTO_INCREMENT");
        }
        return sb.toString();
    }

    private String stringifyPrimaryKeys(SqlColumn t) {
        if ((t.getFlags() & SqlDsl.PRIMARY_KEY) > 0) {
            return t.getName();
        }
        return null;
    }

    private String stringifyUniqueKeys(SqlColumn t) {
        if ((t.getFlags() & SqlDsl.UNIQUE) > 0) {
            return t.getName();
        }
        return null;
    }
}

