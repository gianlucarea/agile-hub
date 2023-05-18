package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.TeacherBooking;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherBookingDaoImpl implements TeacherBookingDao{
    @Override
    public  TeacherBooking getTeacherBookingById(int id) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        TeacherBooking teacherBooking = null;
        PreparedStatement ps = null ;
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
            teacherBooking = new TeacherBooking(teacher_booking_id, user_id, teacher_id,dayOfBooking,sport);
        }

        return teacherBooking;
    }

    @Override
    public boolean insertTeacherBooking(TeacherBooking teacherBooking) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Teacher_Booking (user_id,teacher_id,dayOfBooking,sport) VALUES (?,?,?,?)";

        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, teacherBooking.getUserId());
            pst.setInt(2, teacherBooking.getTeacherId());
            pst.setString(3, teacherBooking.getDayOfBooking());
            pst.setString(4,teacherBooking.getSport().toString());
            int i = pst.executeUpdate();
            if (i==1) {
                return true;}
            else return false;
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
    public List<TeacherBooking> getAllTeacherBookingsForMaestro(int teacher_id) throws SQLException {

        Connection connection = DaoFactory.getConnection();
        PreparedStatement ps = null;
        List<TeacherBooking> listOfTeacherBooking = new ArrayList<>();
        String sql = "select id,user_id,teacher_id,dayOfBooking,sport FROM Teacher_Booking WHERE teacher_id = ?;";

        ps = connection.prepareStatement(sql);
        ps.setInt(1, teacher_id);
        ResultSet rs = ps.executeQuery();

        try {
            while (rs.next()) {
                TeacherBooking tb = new TeacherBooking();
                tb.setTeacherId(rs.getInt("teacher_id"));
                tb.setDayOfBooking(rs.getString("dayOfBooking"));
                tb.setUserId(rs.getInt("user_id"));
                tb.setId(rs.getInt("id"));
                tb.setSport(Enum.valueOf(Sport.class , rs.getString("sport")));
                listOfTeacherBooking.add(tb);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
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

        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, teacherBooking.getUserId());
            pst.setInt(2, teacherBooking.getTeacherId());
            pst.setString(3, teacherBooking.getDayOfBooking());
            pst.setString(4,teacherBooking.getSport().toString());
            ResultSet rs = pst.executeQuery();

            TeacherBooking tb = new TeacherBooking();
            if(rs.next()) {

                tb.setTeacherId(rs.getInt("teacher_id"));
                tb.setDayOfBooking(rs.getString("dayOfBooking"));
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
    public boolean isTeacearBookingFull(int teacher_id, String bookingDate) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        String sql = "SELECT COUNT(id) FROM teacher_booking WHERE teacher_id = ? AND dayOfBooking = ?";

        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, teacher_id);
            pst.setString(2, bookingDate);
            ResultSet rs = pst.executeQuery();

            int result = 3;
            if (rs.next()) {
                result = rs.getInt(1);
            }

            return result >= 3;

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
}
