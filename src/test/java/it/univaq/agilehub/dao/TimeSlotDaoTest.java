package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Pitch;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.TimeSlot;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimeSlotDaoTest {

    private static final DaoFactory daoFactory = new DaoFactory();

    private static final TimeSlotDao timeSlotDao = new TimeSlotDaoImpl();
    Connection connection;

    @BeforeAll
    void setUp() throws SQLException {
        //Database creation
        try {
            Utility.readScript();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotIdTest(){
        String bookingDate = "30/05/2023";
        Pitch p = new Pitch(1,"Calcetto 1", Sport.CALCETTO);

        try{
            ArrayList<Integer> unavailable = timeSlotDao.unavailableTimeSlotId(bookingDate,p);
            assertEquals(false, unavailable.isEmpty());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotIdAllAvailableTest(){
        String bookingDate = "01/06/2023";
        Pitch p = new Pitch(1,"Calcetto 1", Sport.CALCETTO);

        try{
            ArrayList<Integer> unavailable = timeSlotDao.unavailableTimeSlotId(bookingDate,p);
            assertEquals(true, unavailable.isEmpty());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotIdNullDate(){
        String bookingDate = "";
        Pitch p = new Pitch(1,"Calcetto 1", Sport.CALCETTO);

        try{
            ArrayList<Integer> unavailable = timeSlotDao.unavailableTimeSlotId(bookingDate,p);
            assertEquals(true, unavailable.isEmpty());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotIdWrongPitchId(){
        String bookingDate = "";
        Pitch p = new Pitch(0,"Calcetto 1", Sport.CALCETTO);

        try{
            ArrayList<Integer> unavailable = timeSlotDao.unavailableTimeSlotId(bookingDate,p);
            assertEquals(true, unavailable.isEmpty());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotIdWrongPitchNull(){
        String bookingDate = "";
        Pitch p = null;

        assertThrows(NullPointerException.class,() ->{
            timeSlotDao.unavailableTimeSlotId(bookingDate,p);
        });
    }

    @Test
    void unavailableTimeSlotTeacherIdTest(){
        String bookingDate = "30/05/2023";
        User user = new User(7,"Jack","Sparrow");

        try{
            ArrayList<Integer> unavailable = timeSlotDao.unavailableTimeSlotTeacherId(bookingDate,user);
            assertEquals(false, unavailable.isEmpty());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotTeacherIdAllAvailableTest(){
        String bookingDate = "01/06/2023";
        User user = new User(7,"Jack","Sparrow");

        try{
            ArrayList<Integer> unavailable = timeSlotDao.unavailableTimeSlotTeacherId(bookingDate,user);
            assertEquals(true, unavailable.isEmpty());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotTeacherIdNullDate(){
        String bookingDate = "";
        User user = new User(7,"Jack","Sparrow");

        try{
            ArrayList<Integer> unavailable = timeSlotDao.unavailableTimeSlotTeacherId(bookingDate,user);
            assertEquals(true, unavailable.isEmpty());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotTeacherIdWrongIDUser(){
        String bookingDate = "";
        User user = new User(0,"Jack","Sparrow");

        try{
            ArrayList<Integer> unavailable = timeSlotDao.unavailableTimeSlotTeacherId(bookingDate,user);
            assertEquals(true, unavailable.isEmpty());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void unavailableTimeSlotTeacherIdWrongNullUser(){
        String bookingDate = "";
        User user = null;
        assertThrows(NullPointerException.class,() ->{
            timeSlotDao.unavailableTimeSlotTeacherId(bookingDate,user);
        });
    }
    @Test
    void getAllTimeSlotsTest(){
        try {
            ArrayList<TimeSlot> timeSlots = timeSlotDao.getAllTimeSlots();

            assertEquals(false, timeSlots.isEmpty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
