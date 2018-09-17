package com.vova_cons.jdbc_dsl.db;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDeleteQueryTest extends SqlDslTestParent {
    @Test
    public void testDelete() {
        String excepted = "DELETE FROM User WHERE name = 'vova_cons'";
        String actual = new SqlDeleteQuery<>(null, table)
                .where().equalsText("name", "vova_cons").endCondition()
                .getQuery();
        assertEquals(excepted, actual);
    }

}