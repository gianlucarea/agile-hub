package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.TeacherBooking;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherBookingDaoImpl implements TeacherBookingDao{
    @Override
    public  TeacherBooking getTeacherBookingById(int id) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        TeacherBooking teacherBooking = null;
        PreparedStatement ps = null ;
        String sql = "select id,user_id,numberOfPeople,dayOfBooking,sport FROM Teacher_Booking WHERE id = ?;";
        ps = connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()){

            int teacher_booking_id = rs.getInt("id");
            int user_id = rs.getInt("user_id");
            int numberOfPeople = rs.getInt("numberOfPeople");
            String dayOfBooking = rs.getString("dayOfBooking");
            Sport sport = Enum.valueOf(Sport.class , rs.getString("sport")) ;
            teacherBooking = new TeacherBooking(teacher_booking_id, user_id, numberOfPeople,dayOfBooking,sport);
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
}
