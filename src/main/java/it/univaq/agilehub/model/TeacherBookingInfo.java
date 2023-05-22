package it.univaq.agilehub.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TeacherBookingInfo {

    private String studentName;

    private LocalDate bookingDate;

    private String bookingTime;

    public TeacherBookingInfo(String stundentName, LocalDate bookingDate, String bookingTime) {
        this.bookingDate = bookingDate;
        this.studentName = stundentName;
        this.bookingTime = bookingTime;
    }

    public TeacherBookingInfo(String stundentName, String bookingDate, String bookingTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.bookingDate = LocalDate.parse(bookingDate, dateTimeFormatter);
        this.studentName = stundentName;
        this.bookingTime = bookingTime;
    }

    public TeacherBookingInfo() {

    }

    public void setStudentName(String stundentName) {
        this.studentName = stundentName;
    }

    public String getStudentName() {
        return this.studentName;
    }

    //public StringProperty getStudentName

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.bookingDate = LocalDate.parse(bookingDate, dateTimeFormatter);
    }

    public LocalDate getBookingDate() {
        return this.bookingDate;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getBookingTime() {
        return this.bookingTime;
    }
}
