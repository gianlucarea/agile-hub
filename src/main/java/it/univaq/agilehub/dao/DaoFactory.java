package it.univaq.agilehub.dao;
import com.mysql.cj.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DaoFactory {
    public static String url = "jdbc:mysql://localhost:3306/agile_hub_production";
    public static String user = "root";
    public static String password = "password";
    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DaoFactory.url = url;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DaoFactory.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DaoFactory.password = password;
    }

    public static Connection getConnection()
    {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

}
