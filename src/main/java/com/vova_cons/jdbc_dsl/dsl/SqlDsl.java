package com.vova_cons.jdbc_dsl.dsl;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDsl {
    public static final int NOT_NULL = 1;
    public static final int UNIQUE = 2;
    public static final int PRIMARY_KEY = 4;
    public static final int AUTO_INCREMENT = 8;
    public static final int NULL = 16;

    public static <T> SqlDslCreateTableStep<T> createTable(Class<T> type, String name) {
        return new SqlDslCreateTableStep<>(name);
    }
}
