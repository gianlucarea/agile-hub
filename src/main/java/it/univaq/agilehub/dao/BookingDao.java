package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Booking;

import java.sql.SQLException;

public interface BookingDao {
    public boolean createBooking(Booking booking);

    Booking getUserById(int id) throws SQLException;
}
