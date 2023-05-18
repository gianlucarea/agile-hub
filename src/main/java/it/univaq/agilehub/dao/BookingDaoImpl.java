package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Booking;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.utility.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static javax.swing.UIManager.getString;

public class BookingDaoImpl implements BookingDao {
    private static final String insertUser ="insert into agilehub.booking value (?)";

    @Override
    public boolean createBooking(Booking booking) {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Booking (user_id,dateBooking,numberPlayers,sport) VALUES (?,?,?,?)";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1,booking.getUserId());
            pst.setString(2, Utility.dateOfBirthConverter(booking.getDateBooking().toString()));
            pst.setInt(3,booking.getNumberPlayers());
            pst.setString(4, booking.getSport().toString());

            int i = pst.executeUpdate();
            if (i==1) {return true;}
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
    public Booking getUserById(int id) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        Booking booking = null;
        PreparedStatement ps = null ;
        String sql = "select user_id,dateBooking,numberPlayers,sport FROM Booking WHERE id = ?;";
        ps = connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int booking_id = rs.getInt("id");
            int user_id = rs.getInt("user_id");
           LocalDate dateBooking = LocalDate.parse(getString("dateBooking").toString());
            int numberPlayers = rs.getInt("numberPlayers");
            Sport sport = Enum.valueOf(Sport.class , rs.getString("sport")) ;
            booking = new Booking(booking_id,user_id,dateBooking,numberPlayers,sport );
        }
        return booking;
    }

}
