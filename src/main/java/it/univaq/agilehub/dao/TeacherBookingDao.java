package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.TeacherBooking;

import java.sql.SQLException;
import java.util.List;

public interface TeacherBookingDao {

     TeacherBooking getTeacherBookingById(int id) throws SQLException;

     boolean insertTeacherBooking(TeacherBooking teacherBooking) throws SQLException;

     List<TeacherBooking> getAllTeacherBookingsForMaestro(int user_id) throws SQLException;

}
