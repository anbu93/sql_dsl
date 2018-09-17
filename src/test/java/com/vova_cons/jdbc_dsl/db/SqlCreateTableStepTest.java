package com.vova_cons.jdbc_dsl.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlCreateTableStepTest extends SqlDslTestParent {
    @Test
    public void createTableTest() {
        String excepted = "CREATE TABLE User(id INT, name VARCHAR(64), PRIMARY_KEY(id))";
        String actual = new SqlCreateTableStep<>(null, table).getQuery();
        Assert.assertEquals(excepted, actual);
    }

}