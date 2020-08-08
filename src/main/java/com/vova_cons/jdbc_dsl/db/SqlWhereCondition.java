package com.vova_cons.jdbc_dsl.db;

import java.util.Objects;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlWhereCondition<T> {
    private T returnedObject;
    private StringBuilder condition = new StringBuilder();

    public SqlWhereCondition(T returnedObject) {
        this.returnedObject = returnedObject;
    }

    public SqlWhereCondition(T returnedObject, String condition) {
        this.returnedObject = returnedObject;
        this.condition.append(condition);
    }

    public SqlWhereCondition<T> equals(String field, Object value) {
        condition.append(field).append(" = ").append(Objects.toString(value));
        return this;
    }

    public SqlWhereCondition<T> notEquals(String field, Object value) {
        condition.append(field).append(" != ").append(Objects.toString(value));
        return this;
    }
    public SqlWhereCondition<T> equalsText(String field, String value) {
        value = value.replaceAll("'", "").replaceAll("\"", "");
        condition.append(field).append(" = '").append(value).append("'");
        return this;
    }
    public SqlWhereCondition<T> notEqualsText(String field, String value) {
        value = value.replaceAll("'", "").replaceAll("\"", "");
        condition.append(field).append(" != '").append(value).append("'");
        return this;
    }
    public SqlWhereCondition<T> less(String field, Object value) {
        condition.append(field).append(" < ").append(Objects.toString(value));
        return this;
    }
    public SqlWhereCondition<T> lessEquals(String field, Object value) {
        condition.append(field).append(" <= ").append(Objects.toString(value));
        return this;
    }
    public SqlWhereCondition<T> great(String field, Object value) {
        condition.append(field).append(" > ").append(Objects.toString(value));
        return this;
    }
    public SqlWhereCondition<T> greatEquals(String field, Object value) {
        condition.append(field).append(" >= ").append(Objects.toString(value));
        return this;
    }
    public SqlWhereCondition<T> and() {
        condition.append(" AND ");
        return this;
    }
    public SqlWhereCondition<T> or() {
        condition.append(" OR ");
        return this;
    }
    public SqlWhereCondition<T> open() {
        condition.append(" ( ");
        return this;
    }
    public SqlWhereCondition<T> close() {
        condition.append(" ) ");
        return this;
    }
    public SqlWhereCondition<T> custom(String c){
        condition.append(c);
        return this;
    }

    public T endCondition() {
        return returnedObject;
    }

    String getQuery() {
        return condition.toString();
    }
}
