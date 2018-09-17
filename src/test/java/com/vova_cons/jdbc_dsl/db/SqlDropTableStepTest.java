package com.vova_cons.jdbc_dsl.db;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDropTableStepTest extends SqlDslTestParent {
    @Test
    public void dropTableTest() {
        String excepted = "DROP TABLE User";
        String actual = new SqlDropTableStep<User>(null, table).getQuery();
        assertEquals(excepted, actual);
    }
}