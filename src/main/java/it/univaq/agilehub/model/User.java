package it.univaq.agilehub.model;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

public class User {

    private int id, age;
    private String name, surname, password, username;
    private LocalDate dateOfBirth;
    private Type type;
    private Sport sport;
    public User() {
    }

    public User(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public User(int id, String name, String surname, String password, String username, LocalDate dateOfBirth, int age, Type type) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.type = type;
    }

    public User( String name, String surname, String password, String username, LocalDate dateOfBirth, int age, Type type) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.type = type;
    }

    /**
     * Constructor For Admin Master Registration
     */
    public User(String name, String surname, String password, String username, LocalDate dateOfBirth, Type type, Sport sport) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        try {
            this.age = this.ageCalculator(dateOfBirth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.type = type;
        this.sport = sport;
    }

    /**
     * Constructor that includes age calculation
     */
    public User( String name, String surname, String password, String username, LocalDate dateOfBirth, Type type) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        try {
            this.age = this.ageCalculator(dateOfBirth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

    public LocalDate getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(LocalDate dateOfBirth){this.dateOfBirth = dateOfBirth; }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    public int ageCalculator(LocalDate bornDate) throws ParseException {
        try{
            LocalDate l1 = LocalDate.of(bornDate.getYear(),bornDate.getMonthValue(), bornDate.getDayOfMonth());
            LocalDate now1 = LocalDate.now();
            Period diff1 = Period.between(l1, now1);
            return diff1.getYears();
        } catch (NullPointerException e){
            throw e;
        }
    }
}
