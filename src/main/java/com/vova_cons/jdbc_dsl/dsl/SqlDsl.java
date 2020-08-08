package com.vova_cons.jdbc_dsl.dsl;

import com.vova_cons.jdbc_dsl.SqlField;

import java.lang.reflect.Field;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDsl {
    public static final int NOT_NULL = 1;
    public static final int UNIQUE = 2;
    public static final int PRIMARY_KEY = 4;
    public static final int AUTO_INCREMENT = 8;
    public static final int NULL = 16;
    public static final String INTEGER = "INT";
    public static final String BOOL = "BOOL";
    public static final String LONG = "LONG";
    public static final String VARCHAR = "VARCHAR";
    public static final String VARCHAR_32 = "VARCHAR(32)";
    public static final String VARCHAR_64 = "VARCHAR(64)";
    public static final String VARCHAR_128 = "VARCHAR(128)";
    public static final String VARCHAR_256 = "VARCHAR(256)";
    public static final String VARCHAR_512 = "VARCHAR(512)";
    public static final String VARCHAR_1024 = "VARCHAR(1024)";
    public static final String VARCHAR_2048 = "VARCHAR(2048)";
    public static final String VARCHAR_4096 = "VARCHAR(4096)";
    public static final String VARCHAR_8192 = "VARCHAR(8192)";
    public static final String VARCHAR_16384 = "VARCHAR(16384)";
    public static final String TEXT = "TEXT";
    public static final String FLOAT = "FLOAT";
    public static final String DOUBLE = "DOUBLE";

    public static <T> SqlDslCreateTableStep<T> createTable(Class<T> type, String name) {
        return new SqlDslCreateTableStep<>(name);
    }

    public static <T> SqlTable<T> createTableWithAnnotations(Class<T> type, String name) {
        SqlDslCreateTableStep<T> builder = createTable(type, name);
        AnnotationDemarshaller<T> demarshaller = new AnnotationDemarshaller<T>(type);
        for(Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(SqlField.class)) {
                SqlField annotation = field.getAnnotation(SqlField.class);
                String fieldName = annotation.name();
                String fieldType = annotation.type();
                int fieldFlugs = annotation.flags();
                builder.customField(fieldName, fieldType, fieldFlugs);
                demarshaller.addField(fieldName, field, AnnotationDemarshaller.detectType(fieldType));
            }
        }
        return builder.done(demarshaller, demarshaller);
    }
}
