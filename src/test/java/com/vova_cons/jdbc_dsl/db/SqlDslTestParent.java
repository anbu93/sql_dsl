package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlDsl;
import com.vova_cons.jdbc_dsl.dsl.SqlTable;
import org.junit.Before;

import java.sql.Connection;

/**
 * Created by anbu on 17.09.18.
 **/
public class SqlDslTestParent {
    SqlTable<SqlDbSelectQueryStepTest.User> table;
    SqlDb db;

    @Before
    public void setUp() {
        table = SqlDsl.createTable(SqlDbSelectQueryStepTest.User.class, "User")
                .fieldInt("id", SqlDsl.PRIMARY_KEY)
                .fieldVarchar(64, "name")
                .done(
                        (rs) -> new SqlDbSelectQueryStepTest.User(rs.getInt("id"), rs.getString("name")),
                        (template, user) -> template.set("id", user.getId()).set("name", user.getName()).get()
                );
        db = new SqlDb("url", "user", "password") {
            @Override
            public Connection getConnection() {
                return null;
            }
        };
    }

    public static class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
