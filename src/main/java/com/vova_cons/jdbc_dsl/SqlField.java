package com.vova_cons.jdbc_dsl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anbu on 08.08.20.
 **/
@Target(value= ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlField {
    String name();
    String type();
    int flags() default 0;
}