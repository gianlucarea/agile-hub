package it.univaq.agilehub.dao;
import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DaoFactory {
    public static final String URL = "jdbc:mysql://localhost:3306/agile_hub_production";
    public static final String USER = "root";
    public static final String PASS = "password";

    public static Connection getConnection()
    {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

}
