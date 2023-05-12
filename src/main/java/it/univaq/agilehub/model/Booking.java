package it.univaq.agilehub.model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private LocalDate dateBooking;
    private int numberPlayers =0;
    private Sport sport;

    public Booking(int id, LocalDate dateBooking, int numberPlayers, Sport sport) {
        this.id = id;
        this.dateBooking = dateBooking;
        this.numberPlayers = numberPlayers;
        this.sport = sport;
    }

    public Booking(LocalDate dateBooking, int numberPlayers, Sport sport) {
        this.dateBooking = dateBooking;
        this.numberPlayers = numberPlayers;
        this.sport = sport;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDateBooking() {
        return dateBooking;
    }

    public int getNumberPlayers() {
        return numberPlayers;
    }

    public void setId(int id) {
        this.id = id;
    }

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
