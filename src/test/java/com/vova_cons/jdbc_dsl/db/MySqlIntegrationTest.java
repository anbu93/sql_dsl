package com.vova_cons.jdbc_dsl.db;

import com.vova_cons.jdbc_dsl.dsl.SqlDsl;
import com.vova_cons.jdbc_dsl.dsl.SqlTable;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by anbu on 08.08.20.
 **/
public class MySqlIntegrationTest {
    private static final String ID = "id";
    private static final String NAME = "name";
    private SqlTable<User> table;
    private SqlDb db;
    private User[] user = new User[] {
            new User(0, "user_1"),
            new User(1, "user_2"),
            new User(2, "user_3"),
            new User(3, "пользователь_4"),
            new User(4, "user_5"),
    };
    private boolean isSuccess = true;
    private StringBuilder result = new StringBuilder();

    public MySqlIntegrationTest() {
        table = SqlDsl.createTable(User.class, "User")
                .fieldInt(ID, SqlDsl.PRIMARY_KEY)
                .fieldVarchar(64, NAME)
                .done(
                        (rs) -> new User(rs.getInt("id"), rs.getString("name")),
                        (template, user) -> template.set(ID, user.getId()).set(NAME, user.getName()).get()
                );
        db = new SqlDb("jdbc:mysql://127.0.0.1/sql_dsl_test", "anbu", "bergen14");
    }

    @Test
    public void integrationTest() throws InterruptedException {
        db.init();
        test();
        Assert.assertTrue(result.toString(), isSuccess);
    }

    private void test() throws InterruptedException {
        assertTrue("Drop table", db.dropTable(table));
        assertTrue("Create table", db.createTable(table));
        assertEquals("Insert into 1 object", 1, db.insertInto(table).execute(user[0]));
        assertEquals("Insert into 2 objects", 2, db.insertInto(table).execute(user[1], user[2]));
        List<User> selectFromWhereId0 = db.selectFrom(table).where().equals(ID, 0).endCondition().execute();
        assertEquals("Select from where id 0 size", 1, selectFromWhereId0.size());
        assertEquals("Select from where id 0", user[0], selectFromWhereId0.get(0));
        List<User> selectFromWhereIdgte0andlt2 = db.selectFrom(table).where()
                .greatEquals(ID, 0).and().less(ID, 2)
                .endCondition().execute();
        assertEquals("Select from where 0 <= id < 2 size", 2, selectFromWhereIdgte0andlt2.size());
        assertEquals("Select from where 0 <= id < 2 objects", new LinkedList<>(Arrays.asList(user[0], user[1])), selectFromWhereIdgte0andlt2);
        assertEquals("Delete from where id > 1", 1, db.deleteFrom(table).where().great(ID, 1).endCondition().execute());
        List<User> selectFromAll = db.selectFrom(table).execute();
        assertEquals("Select from where * size", 2, selectFromAll.size());
        assertEquals("Select from where * objects", new LinkedList<>(Arrays.asList(user[0], user[1])), selectFromAll);
        User updateUser = new User(0, "new_user");
        assertEquals("Update where id 0", 1, db.update(table).execute(updateUser));
        assertEquals("Select updated user", updateUser, db.selectFrom(table).where().equals(ID, updateUser.id).endCondition().execute().get(0));
        assertEquals("Insert into russian symbols", 1, db.insertInto(table).execute(user[3]));
        User rusUser = db.selectFrom(table).where().equals(ID, 3).endCondition().execute().get(0);
        assertEquals("Select russian symbols", "пользователь_4", rusUser.getName());
        db.setTimeout(1);
        db.recreateConnection();
        Thread.sleep(3);
        List<User> allUsers = db.selectFrom(table).execute();
        assertEquals("Select after timeout", 3, allUsers.size());
    }


    //region assertions
    private void assertTrue(String tag, boolean isSuccess) {
        result.append(tag).append("...");
        if (isSuccess) {
            result.append("OK\n");
        } else {
            result.append("ERROR: condition false\n");
            this.isSuccess = false;
        }
    }

    private void assertEquals(String tag, int excepted, int actual) {
        result.append(tag).append("...");
        if (excepted == actual) {
            result.append("OK\n");
        } else {
            result.append("ERROR: ")
                    .append("not equals excepted=").append(excepted)
                    .append(" actual=").append(actual).append("\n");
            this.isSuccess = false;
        }
    }

    private void assertEquals(String tag, Object excepted, Object actual) {
        result.append(tag).append("...");
        if (Objects.equals(excepted, actual)) {
            result.append("OK\n");
        } else {
            result.append("ERROR ")
                    .append("not equals excepted=").append(excepted)
                    .append(" actual=").append(actual).append("\n");
            this.isSuccess = false;
        }
    }

    private void assertEquals(String tag, Collection excepted, Collection actual) {
        result.append(tag).append("...");
        if (Objects.equals(excepted, actual)) {
            result.append("OK\n");
        } else {
            result.append("ERROR ")
                    .append("not equals excepted=").append(excepted)
                    .append(" actual=").append(actual).append("\n");
            this.isSuccess = false;
        }
    }
    //endregion

    public static class User implements SqlUpdateConditionable {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return id == user.id &&
                    Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean isConditionColumnTextType() {
            return false;
        }

        @Override
        public String getConditionColumnName() {
            return ID;
        }
    }
}
