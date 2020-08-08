package com.vova_cons.jdbc_dsl.db;

/**
 * Created by anbu on 08.08.20.
 **/
public interface SqlUpdateConditionable {
    boolean isConditionColumnTextType();
    String getConditionColumnName();
}
