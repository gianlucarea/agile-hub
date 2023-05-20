package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Pitch;
import it.univaq.agilehub.model.TimeSlot;
import it.univaq.agilehub.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TimeSlotDao {

    ArrayList<Integer> unavailableTimeSlotId(String bookingDate, Pitch p) throws SQLException;

    ArrayList<Integer> unavailableTimeSlotTeacherId(String bookingDate, User u) throws SQLException;

    ArrayList<TimeSlot> getAllTimeSlots() throws SQLException;
}
