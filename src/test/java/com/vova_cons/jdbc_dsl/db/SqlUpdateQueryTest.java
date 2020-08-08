package com.vova_cons.jdbc_dsl.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by anbu on 08.08.20.
 **/
public class SqlUpdateQueryTest extends SqlDslTestParent {
    @Test
    public void insertIntoTest() {
        String excepted = "UPDATE User SET id = {id}, name = '{name}' WHERE id = {id}";
        String actual = new SqlUpdateQuery<>(null, table).getQuery(new User(0, "name"));
        assertEquals(excepted, actual);
    }
}
