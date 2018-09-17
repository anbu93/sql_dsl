package com.vova_cons.jdbc_dsl.dsl;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by anbu on 17.09.18.
 **/
public interface Marshaller<T> {
    T marshall(ResultSet rs) throws SQLException;
}
