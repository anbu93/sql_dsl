package com.vova_cons.jdbc_dsl.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDbSelectQueryStepTest extends SqlDslTestParent {
    @Test
    public void simpleSelectTest() {
        String excepted = "SELECT id, name FROM User";
        String actual = db.selectFrom(table).createQuery();
        assertEquals(excepted, actual);
    }

    @Test
    public void selectWhereIdTest() {
        String excepted = "SELECT id, name FROM User WHERE id = 3";
        String actual = db.selectFrom(table)
                .where().equals("id", 3).endCondition()
                .createQuery();
        assertEquals(excepted, actual);
    }

    @Test
    public void selectWhereIdAndNameTest() {
        String excepted = "SELECT id, name FROM User WHERE id > 3 AND name = 'vova_cons'";
        String actual = db.selectFrom(table)
                .where().great("id", 3).and().equalsText("name", "vova_cons").endCondition()
                .createQuery();
        assertEquals(excepted, actual);
    }

    @Test
    public void selectRawWhereTest() {
        String excepted = "SELECT id, name FROM User WHERE id > 3 AND name = 'vova_cons'";
        String actual = db.selectFrom(table)
                .where("id > 3 AND name = 'vova_cons'")
                .createQuery();
        assertEquals(excepted, actual);
    }
}