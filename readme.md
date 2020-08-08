# Обертка для удобной работы с JDBC

## Пример использования

Создаем класс модели
```java
class User {
    @SqlField(name = "id", type = SqlDsl.INTEGER, flags = SqlDsl.PRIMARY_KEY)
    private int id;
    @SqlField(name = "name", type = SqlDsl.VARCHAR_64)
    private String name;
    
    public User() {}
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
```

И класс для базы данных
```java
class Database {
    private SqlTable<User> table;
    private SqlDb db;
    
    public Database(String url, String user, String pass) {
        // example url "jdbc:mysql://127.0.0.1/sql_dsl_test"
        table = SqlDsl.createTableWithAnnotations(User.class, "User");
        db = new SqlDb(url, user, pass); 
    }
    
    public boolean addUser(User user) {
        return db.insertInto(table).execute(user) == 1;
    }
    
    public List<User> getUsers(User user) {
        return db.selectFrom(table).execute();
    }
    
    public boolean removeUser(User user) {
        return db.deleteFrom(table)
            .where().equals("id", user.getId()).endCondition()
            .execute() == 1;
    }
}
```

Более детально использование методов можно посмотреть в классе `MySqlIntegrationTest`

## Версии

0.3.0: Добавлена возможность связывать класс с базой данных через аннотацию @SqlField