package com.vova_cons.jdbc_dsl.dsl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anbu on 08.08.20.
 **/
class AnnotationDemarshaller<T> implements Marshaller<T>, Demarshaller<T> {
    public static final int INTEGER = 0;
    public static final int BOOL = 1;
    public static final int STRING = 2;
    public static final int LONG = 3;
    public static final int DOUBLE = 4;
    public static final int FLOAT = 5;
    private final Class<T> type;
    private Map<String, Field> annotatedFields = new HashMap<>();
    private Map<String, Integer> fieldTypes = new HashMap<>();

    public static int detectType(String type) {
        if (type.equals(SqlDsl.INTEGER)) {
            return INTEGER;
        }
        if (type.equals(SqlDsl.BOOL)) {
            return BOOL;
        }
        if (type.equals(SqlDsl.DOUBLE)) {
            return DOUBLE;
        }
        if (type.equals(SqlDsl.FLOAT)) {
            return FLOAT;
        }
        if (type.equals(SqlDsl.LONG)) {
            return LONG;
        }
        return STRING;
    }

    public AnnotationDemarshaller(Class<T> type) {
        this.type = type;
    }

    public void addField(String name, Field field, int type) {
        field.setAccessible(true);
        annotatedFields.put(name, field);
        fieldTypes.put(name, type);
    }

    @Override
    public T marshall(ResultSet rs) {
        try {
            T result = type.newInstance();
            for(String fieldName : annotatedFields.keySet()) {
                Field field = annotatedFields.get(fieldName);
                Integer fieldType = fieldTypes.get(fieldName);
                switch(fieldType) {
                    case INTEGER: field.setInt(result, rs.getInt(fieldName)); break;
                    case BOOL: field.setBoolean(result, rs.getBoolean(fieldName)); break;
                    case STRING: field.set(result, rs.getString(fieldName)); break;
                    case LONG: field.setLong(result, rs.getLong(fieldName)); break;
                    case DOUBLE: field.setDouble(result, rs.getDouble(fieldName)); break;
                    case FLOAT: field.setFloat(result, rs.getFloat(fieldName)); break;
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String demarshall(StringTemplate template, T object) {
        try {
            String old = template.get();
            for (String fieldName : annotatedFields.keySet()) {
                Field field = annotatedFields.get(fieldName);
                Integer fieldType = fieldTypes.get(fieldName);
                switch (fieldType) {
                    case INTEGER:  template.set(fieldName, field.getInt(object)); break;
                    case BOOL:  template.set(fieldName, field.getBoolean(object)); break;
                    case STRING: template.set(fieldName, field.get(object)); break;
                    case LONG: template.set(fieldName, field.getLong(object)); break;
                    case DOUBLE: template.set(fieldName, field.getDouble(object)); break;
                    case FLOAT: template.set(fieldName, field.getFloat(object)); break;
                }
            }
            String result = template.get();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
