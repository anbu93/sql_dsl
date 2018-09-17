package com.vova_cons.jdbc_dsl.tool;

import java.util.Collection;

/**
 * Created by anbu on 17.09.18.
 **/
public class StringBuilderAppender {
    public static <T> StringBuilder join(StringBuilder sb, Collection<T> collection, Stringify<T> stringify, String delimeter) {
        boolean isFirst = true;
        for(T obj : collection) {
            String value = stringify.stringify(obj);
            if (value != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(delimeter);
                }
                sb.append(value);
            }
        }
        return sb;
    }


    public interface Stringify<T> {
        String stringify(T obj);
    }
}
