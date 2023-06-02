package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Booking;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.utility.Utility;

import java.sql.*;
import java.time.LocalDate;

import static javax.swing.UIManager.getString;

public class BookingDaoImpl implements BookingDao {
    private static final String insertUser ="insert into agilehub.booking value (?)";

    @Override
    public int insertBooking(Booking booking) {
        Connection connection = DaoFactory.getConnection();
        int id = 0;
        String sql = "INSERT INTO Booking (user_id,dateBooking,numberPlayers,sport) VALUES (?,?,?,?)";
        PreparedStatement pst = null;
        if( booking.getUserId() == 0 || booking.getDateBooking().isBefore(LocalDate.now())){
            throw new IllegalArgumentException();
        }
        try {
            pst = connection.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,booking.getUserId());
            pst.setString(2, Utility.dateOfBirthConverter(booking.getDateBooking().toString()));
            pst.setInt(3,booking.getNumberPlayers());
            pst.setString(4, booking.getSport().toString());
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            return id;
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
    public void insertTimeBooking(int pitch_id, int booking_id, String dateBooking, int time_id) {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Time_Booking (pitch_id,booking_id,dateBooking,time_id) VALUES (?,?,?,?)";
        PreparedStatement pst = null;

        if( pitch_id <= 0 || booking_id <=0 || time_id <=0 ){
            throw new IllegalArgumentException();
        }
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1,pitch_id);
            pst.setInt(2, booking_id);
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
