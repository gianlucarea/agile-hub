package it.univaq.agilehub.model;

import java.time.LocalDate;

public class TeacherBooking {
    private int id, userId, teacherId;
    private LocalDate dayOfBooking;
    private Sport sport;

    public TeacherBooking() {
    }

    public TeacherBooking(int id, int userId, int teacherId, LocalDate dayOfBooking, Sport sport) {
        this.id = id;
        this.userId = userId;
        this.teacherId = teacherId;
        this.dayOfBooking = dayOfBooking;
        this.sport = sport;
    }

    public TeacherBooking(int userId, int teacherId, LocalDate dayOfBooking, Sport sport) {
        this.id = id;
        this.userId = userId;
        this.teacherId = teacherId;
        this.dayOfBooking = dayOfBooking;
        this.sport = sport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public LocalDate getDayOfBooking() {
        return dayOfBooking;
    }

    public void setDayOfBooking(LocalDate dayOfBooking) {
        this.dayOfBooking = dayOfBooking;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return "TeacherBooking{" +
                "id=" + id +
                ", userId=" + userId +
                ", teacherId=" + teacherId +
                ", dayOfBooking='" + dayOfBooking + '\'' +
                ", sport=" + sport +
                '}';
    }

    public boolean equals(TeacherBooking tb) {
        return this.userId == tb.userId && this.teacherId == tb.teacherId && this.dayOfBooking.equals(tb.dayOfBooking) && this.sport == tb.sport;
    }
}
