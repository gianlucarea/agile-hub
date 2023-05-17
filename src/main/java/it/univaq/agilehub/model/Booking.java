package it.univaq.agilehub.model;

import java.time.LocalDate;

public class Booking {


    private int id;
    private int userId;
    private LocalDate dateBooking;
    private int numberPlayers =0;
    private Sport sport;




    public Booking(int id, int userId, LocalDate dateBooking, int numberPlayers, Sport sport) {

        this.id = id;
        this.userId = userId;
        this.dateBooking = dateBooking;
        this.numberPlayers = numberPlayers;
        this.sport = sport;
    }

    public Booking(int userId,LocalDate dateBooking, int numberPlayers, Sport sport) {
        this.userId = userId;
        this.dateBooking = dateBooking;
        this.numberPlayers = numberPlayers;
        this.sport = sport;
    }

    public Booking() {

    }

    public int getId() {
        return id;
    }

    public int getUserId() {return userId;}
    public LocalDate getDateBooking() {
        return dateBooking;
    }

    public int getNumberPlayers() {
        return numberPlayers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {this.userId = userId;}
    public void setDateBooking(LocalDate dateBooking) {
        this.dateBooking = dateBooking;
    }

    public void setNumberPlayers(int numberPlayers) {
        this.numberPlayers = numberPlayers;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}
