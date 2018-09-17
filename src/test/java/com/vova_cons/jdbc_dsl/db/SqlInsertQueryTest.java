package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlDsl;
import com.vova_cons.jdbc_dsl.dsl.SqlTable;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlInsertQueryTest extends SqlDslTestParent {
    @Test
    public void insertIntoTest() {
        String excepted = "INSERT INTO User(id, name) VALUES({id}, '{name}')";
        String actual = new SqlInsertQuery<>(null, table).getQuery();
        assertEquals(excepted, actual);
    }


    @Test
    public void insertIntoAutoIncrementIdTest() {
        SqlTable<User> table = SqlDsl.createTable(User.class, "User")
                .fieldInt("id", SqlDsl.AUTO_INCREMENT | SqlDsl.PRIMARY_KEY)
                .fieldVarchar(32, "name")
                .done((rs) -> new User(0, null), ((template, object) -> ""));
        String excepted = "INSERT INTO User(name) VALUES('{name}')";
        String actual = new SqlInsertQuery<>(null, table).getQuery();
        assertEquals(excepted, actual);
    }

}