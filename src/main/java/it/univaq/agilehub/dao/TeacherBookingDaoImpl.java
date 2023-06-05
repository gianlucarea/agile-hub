package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.TeacherBooking;
import it.univaq.agilehub.model.TimeSlot;
import it.univaq.agilehub.utility.Utility;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TeacherBookingDaoImpl implements TeacherBookingDao{
    @Override
    public  TeacherBooking getTeacherBookingById(int id) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        TeacherBooking teacherBooking = null;
        PreparedStatement ps = null ;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String sql = "select id,user_id,teacher_id,dayOfBooking,sport FROM Teacher_Booking WHERE id = ?;";
        ps = connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){
            int teacher_booking_id = rs.getInt("id");
            int user_id = rs.getInt("user_id");
            int teacher_id = rs.getInt("teacher_id");
            String dayOfBooking = rs.getString("dayOfBooking");
            Sport sport = Enum.valueOf(Sport.class , rs.getString("sport")) ;
            LocalDate dayOfBookingTolocalDate = LocalDate.parse(dayOfBooking,dateTimeFormatter);
            teacherBooking = new TeacherBooking(teacher_booking_id, user_id, teacher_id,dayOfBookingTolocalDate,sport);
        }
        return teacherBooking;
    }

    @Override
    public int insertTeacherBooking(TeacherBooking teacherBooking) {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Teacher_Booking (user_id,teacher_id,dayOfBooking,sport) VALUES (?,?,?,?)";
        PreparedStatement pst = null;
        int teacher_booking_id = 0;

        if(teacherBooking.getUserId() <= 0 || teacherBooking.getTeacherId() <= 0 || teacherBooking.getDayOfBooking().isBefore(LocalDate.now())){
            throw new IllegalArgumentException();
        }
        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, teacherBooking.getUserId());
            pst.setInt(2, teacherBooking.getTeacherId());
            pst.setString(3, Utility.dateOfBirthConverter(teacherBooking.getDayOfBooking().toString()));
            pst.setString(4,teacherBooking.getSport().toString());

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next()){
                teacher_booking_id = rs.getInt(1);
            }
            return teacher_booking_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pst != null) {
                try { pst.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
    }

    @Override
    public void insertTimeTeacherBooking(int teacher_id, int teacher_booking_id, String dateBooking, int time_id) {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Time_TeacherBooking (teacher_id,teacher_booking_id,dateBooking,time_id) VALUES (?,?,?,?)";
        PreparedStatement pst = null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(teacher_id <= 0 || teacher_booking_id <=0 || time_id <= 0 || LocalDate.parse(dateBooking,dateTimeFormatter).isBefore(LocalDate.now())){
            throw new IllegalArgumentException();
        }
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1,teacher_id);
            pst.setInt(2, teacher_booking_id);
            pst.setString(3, Utility.dateOfBirthConverter(dateBooking));
            pst.setInt(4, time_id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pst != null) {
                try { pst.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
    }

    @Override
    public TimeSlot getTimeTeacherBooking(int teacher_booking_id) {
        Connection connection = DaoFactory.getConnection();
        String sql = "SELECT t.id, t.time_slot FROM Time_TeacherBooking b, Time_Slot t WHERE b.teacher_booking_id = ? AND b.time_id = t.id";
        PreparedStatement pst = null;
        if(teacher_booking_id <= 0 ){
            throw new IllegalArgumentException();
        }
        TimeSlot result = new TimeSlot();
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1,teacher_booking_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                result.setId(rs.getInt(1));
                result.setTime_slot(rs.getString(2));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pst != null) {
                try { pst.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
        return result;
    }

    @Override
    public List<TeacherBooking> getAllTeacherBookingsForMaestro(int teacher_id) throws SQLException {

        Connection connection = DaoFactory.getConnection();
        PreparedStatement ps = null;
        List<TeacherBooking> listOfTeacherBooking = new ArrayList<>();
        String sql = "select id,user_id,teacher_id,dayOfBooking,sport FROM Teacher_Booking WHERE teacher_id = ?;";
        if(teacher_id <= 0 ){
            throw new IllegalArgumentException();
        }
        ps = connection.prepareStatement(sql);
        ps.setInt(1, teacher_id);
        ResultSet rs = ps.executeQuery();

        try {
            while (rs.next()) {
                TeacherBooking tb = new TeacherBooking();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                tb.setTeacherId(rs.getInt("teacher_id"));
                LocalDate dayOfBookingTolocalDate = LocalDate.parse(rs.getString("dayOfBooking"), dateTimeFormatter);
                tb.setDayOfBooking(dayOfBookingTolocalDate);
                tb.setUserId(rs.getInt("user_id"));
                tb.setId(rs.getInt("id"));
                tb.setSport(Enum.valueOf(Sport.class , rs.getString("sport")));
                listOfTeacherBooking.add(tb);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }catch (DateTimeParseException e){
            throw new DateTimeParseException(e.getMessage(), e.getParsedString(), e.getErrorIndex()) ;
        }
        finally {
            if (ps != null) {
                try { ps.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
        return listOfTeacherBooking;
    }

    @Override
    public boolean doesTeacherBookingAlreadyExist(TeacherBooking teacherBooking) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        String sql = "SELECT user_id, teacher_id, dayOfBooking, sport FROM Teacher_Booking WHERE user_id = ? AND teacher_id = ? AND dayOfBooking = ? AND sport = ?";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(teacherBooking.getUserId() <= 0 || teacherBooking.getTeacherId() <= 0){
            throw new IllegalArgumentException();
        }
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, teacherBooking.getUserId());
            pst.setInt(2, teacherBooking.getTeacherId());
            String date = Utility.dateOfBirthConverter(teacherBooking.getDayOfBooking().toString());
            pst.setString(3,date );
            pst.setString(4,teacherBooking.getSport().toString());
            ResultSet rs = pst.executeQuery();

            TeacherBooking tb = new TeacherBooking();
            if(rs.next()) {
                tb.setTeacherId(rs.getInt("teacher_id"));
                LocalDate dayOfBookingTolocalDate = LocalDate.parse(rs.getString("dayOfBooking"),dateTimeFormatter);
                tb.setDayOfBooking(dayOfBookingTolocalDate);
                tb.setUserId(rs.getInt("user_id"));
                tb.setSport(Enum.valueOf(Sport.class , rs.getString("sport")));
            }
            return teacherBooking.equals(tb);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pst != null) {
                try { pst.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
    }

    @Override
    public boolean isTeacherBookingFull(int teacher_id, String bookingDate) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        String sql = "SELECT COUNT(id) FROM Teacher_Booking WHERE teacher_id = ? AND dayOfBooking = ?";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate.parse(bookingDate,dateTimeFormatter);

        if(teacher_id<= 0){
            throw new IllegalArgumentException();
        }
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, teacher_id);
            pst.setString(2, bookingDate);
            ResultSet rs = pst.executeQuery();

            int result = 3;
            if (rs.next()) {
                result = rs.getInt(1);}
            return result >= 3;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DateTimeParseException e){
            throw new DateTimeParseException(e.getMessage(), e.getParsedString(), e.getErrorIndex()) ;
        }finally {
            if (pst != null) {
                try { pst.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
    }
}
