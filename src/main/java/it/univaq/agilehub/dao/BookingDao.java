package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Booking;

import java.sql.SQLException;

public interface BookingDao {
     int insertBooking(Booking booking);

     void insertTimeBooking(int pitch_id, int booking_id, String dateBooking, int time_id);

    Booking getUserById(int id) throws SQLException;
}
