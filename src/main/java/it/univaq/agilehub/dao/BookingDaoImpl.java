package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingDaoImpl implements BookingDao {
    private static final String insertUser ="insert into agilehub.booking value (?)";

    @Override
    public boolean createBooking(Booking booking) {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Booking (dateBooking,numberPlayers,sport) VALUES (?,?,?)";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, booking.getDateBooking().toString());
            pst.setInt(2,booking.getNumberPlayers());
            pst.setString(3, booking.getSport().toString());

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
