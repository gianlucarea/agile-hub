package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.TeacherBooking;
import it.univaq.agilehub.model.TimeSlot;

import java.sql.SQLException;
import java.util.List;

public interface TeacherBookingDao {

     TeacherBooking getTeacherBookingById(int id) throws SQLException;

     int insertTeacherBooking(TeacherBooking teacherBooking) throws SQLException;

     void insertTimeTeacherBooking(int teacher_id, int teacher_booking_id, String dateBooking, int time_id);

     TimeSlot getTimeTeacherBooking(int teacher_booking_id);

     List<TeacherBooking> getAllTeacherBookingsForMaestro(int user_id) throws SQLException;

     boolean doesTeacherBookingAlreadyExist(TeacherBooking teacherBooking) throws SQLException;

     boolean isTeacherBookingFull(int teacher_id, String bookingDate) throws SQLException;

}
