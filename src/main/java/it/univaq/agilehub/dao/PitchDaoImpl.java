package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PitchDaoImpl implements PitchDao{

    @Override
    public List<Pitch> getPitchBySport(String Type) throws SQLException {

        Connection connection = DaoFactory.getConnection();
        PreparedStatement ps = null;
        List<Pitch> listOfPitch = new ArrayList<>();
        String sql = "select id,name,type FROM Pitch WHERE type = ?;";

        ps = connection.prepareStatement(sql);
        ps.setString(1, Type);
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                Pitch pitch = new Pitch();
                pitch.setId(rs.getInt("id"));
                pitch.setName(rs.getString("name"));
                pitch.setSport(Enum.valueOf(Sport.class, rs.getString("type")));
                listOfPitch.add(pitch);
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
        return listOfPitch;
    }
}
