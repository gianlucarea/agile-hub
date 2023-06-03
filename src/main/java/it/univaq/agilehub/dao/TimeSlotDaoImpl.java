package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Pitch;
import it.univaq.agilehub.model.TimeSlot;
import it.univaq.agilehub.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TimeSlotDaoImpl implements TimeSlotDao{
    @Override
    public ArrayList<Integer> unavailableTimeSlotId(String bookingDate, Pitch p) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        PreparedStatement ps = null;
        ArrayList<Integer> listOfTimeSlots = new ArrayList<>();
        String sql = "select time_id FROM Time_Booking WHERE dateBooking = ? AND pitch_id = ?;";

        ps = connection.prepareStatement(sql);
        ps.setString(1, bookingDate);
        ps.setInt(2,p.getId());

        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                listOfTimeSlots.add(rs.getInt("time_id"));
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
        return listOfTimeSlots;
    }

    @Override
    public ArrayList<Integer> unavailableTimeSlotTeacherId(String bookingDate, User t) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        PreparedStatement ps = null;
        ArrayList<Integer> listOfTimeSlots = new ArrayList<>();
        String sql = "select time_id FROM Time_TeacherBooking WHERE dateBooking = ? AND teacher_id = ?;";

        ps = connection.prepareStatement(sql);
        ps.setString(1, bookingDate);
        try{
            ps.setInt(2,t.getId());
        }catch (NullPointerException e){
            throw e;
        }

        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                listOfTimeSlots.add(rs.getInt("time_id"));
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
        return listOfTimeSlots;
    }

    @Override
    public ArrayList<TimeSlot> getAllTimeSlots() throws SQLException {
        Connection connection = DaoFactory.getConnection();
        PreparedStatement ps = null;
        ArrayList<TimeSlot> listOfTimeSlots = new ArrayList<>();
        String sql = "select id, time_slot FROM Time_Slot;";

        ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setId(rs.getInt("id"));
                timeSlot.setTime_slot(rs.getString("time_slot"));
                listOfTimeSlots.add(timeSlot);
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
        return listOfTimeSlots;
    }

}

