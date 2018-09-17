package com.vova_cons.jdbc_dsl.dsl;

/**
 * Created by anbu on 17.09.18.
 **/
public interface Demarshaller<T> {
    String demarshall(StringTemplate template, T object);
}
