package it.univaq.agilehub.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class User {

    private int id;
    private String name;
    private String surname;
    private String password;
    private String username;

    private String dateOfBirth;
    private int age;
    private Type type;

    public User() {
    }

    public User(int id, String name, String surname, String password, String username, String dateOfBirth, int age, Type type) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.type = type;
    }

    public User( String name, String surname, String password, String username, String dateOfBirth, int age, Type type) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.type = type;
    }

    /**
     * Constructor that includes age calculation
     */
    public User( String name, String surname, String password, String username, String dateOfBirth, Type type) {
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

    public String getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(String dateOfBirth){this.dateOfBirth = dateOfBirth; }
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

    public int ageCalculator(String bornDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse(bornDate);
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);

        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(l1, now1);

        return diff1.getYears();
    }

    /**
     *   yyyy-mm-dd TO dd/mm/yyyy
     */
    public static String dateOfBirthConverter(String paramFromFX){
        String dateOfBirth = new String();
        String dateToSplit =paramFromFX ;
        String[] arrOfSplit = dateToSplit.split("-");
        for (int i = arrOfSplit.length - 1 ; i > -1; i--){
            dateOfBirth += arrOfSplit[i] + "/";
        }
        dateOfBirth = (dateOfBirth.substring(0, dateOfBirth.length() - 1));
        return dateOfBirth;
    }


}
