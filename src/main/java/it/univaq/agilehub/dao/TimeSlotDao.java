package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Pitch;
import it.univaq.agilehub.model.TimeSlot;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TimeSlotDao {

    ArrayList<Integer> unavailableTimeSlotId(String bookingDate, Pitch p) throws SQLException;

    ArrayList<TimeSlot> getAllTimeSlots() throws SQLException;
}
