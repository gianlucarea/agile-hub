package it.univaq.agilehub.model;

public class User {

    private String name, surname, fiscal_code;
    private int age;
    private Type type;

    public User() {
    }

    public User(String name, String surname, String fiscal_code, int age, Type type) {
        this.name = name;
        this.surname = surname;
        this.fiscal_code = fiscal_code;
        this.age = age;
        this.type = type;
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

    public String getFiscal_code() {
        return fiscal_code;
    }

    public void setFiscal_code(String fiscal_code) {
        this.fiscal_code = fiscal_code;
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

    public String toCheck(String name, String surname){
        return name + " " + surname;
    }
}
