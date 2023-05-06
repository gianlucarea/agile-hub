package it.univaq.agilehub.model;

public class User {

    private int id;
    private String name;
    private String surname;
    private String password;
    private String username;
    private int age;
    private Type type;

    public User() {
    }

    public User(int id, String name, String surname, String password, String username, int age, Type type) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.age = age;
        this.type = type;
    }

    public User( String name, String surname, String password, String username, int age, Type type) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.age = age;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", type=" + type +
                '}';
    }

    public String toCheck(String name, String surname){
        return name + " " + surname;
    }
}
