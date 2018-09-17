package com.vova_cons.jdbc_dsl.dsl;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlColumn {
    private final String name;
    private final String type;
    private final int flags;

    public SqlColumn(String name, String type, int flags) {
        this.name = name;
        this.type = type;
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getFlags() {
        return flags;
    }
}
