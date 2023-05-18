package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.utility.Utility;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.*;


public class UserDaoImpl implements UserDao{
    Encoder encoder = Base64.getEncoder();
    Decoder decoder = Base64.getDecoder();
    private static final String insertUser ="insert into agilehub.user value (?)";

    @Override
    public boolean registration(User user) {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Users (name,surname,password,username,dateOfBirth,age,type) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getSurname());
            pst.setString(3, encoder.encodeToString(user.getPassword().getBytes()));
            pst.setString(4, user.getUsername());
            String dateOfBirth = Utility.dateOfBirthConverter(user.getDateOfBirth().toString());
            pst.setString(5, dateOfBirth);
            pst.setInt(6,user.getAge());
            pst.setString(7,user.getType().toString());
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
    public boolean registrationAdmin(User user) {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Users (name,surname,password,username,dateOfBirth,age,type,sport) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getSurname());
            pst.setString(3, encoder.encodeToString(user.getPassword().getBytes()));
            pst.setString(4, user.getUsername());
            String dateOfBirth = Utility.dateOfBirthConverter(user.getDateOfBirth().toString());
            pst.setString(5, dateOfBirth.toString());
            pst.setInt(6,user.getAge());
            pst.setString(7,user.getType().toString());
            pst.setString(8,user.getSport().toString());
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
    public User getUserById(int id) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        User user = null;
        PreparedStatement ps = null ;
        String sql = "select id,name,surname,password,username,dateOfBirth,age,type FROM Users WHERE id = ?;";
        ps = connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
                int user_id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String password = rs.getString("password");
                String username = rs.getString("username");
                String dateOfBirth = rs.getString("dateOfBirth");
                int age = rs.getInt("age");
                Type type = Enum.valueOf(Type.class , rs.getString("type")) ;
                LocalDate dateOfBirthTolocalDate = LocalDate.parse(dateOfBirth,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                user = new User(user_id,name,surname,password,username,dateOfBirthTolocalDate,age,type );
            }
            return user;


    }

    @Override
    public User getUserByUsername(String username)  {
        Connection connection = DaoFactory.getConnection();
        User user = null;
        PreparedStatement ps =  null;
        String sql = "select id,name,surname,password,username,age,dateOfBirth,type FROM Users WHERE username = ?;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int user_id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String password = rs.getString("password");
                String usernameFromDB = rs.getString("username");
                String dateOfBirth = rs.getString("dateOfBirth");
                int age = rs.getInt("age");
                Type type = Enum.valueOf(Type.class , rs.getString("type")) ;
                LocalDate dateOfBirthTolocalDate = LocalDate.parse(dateOfBirth);
                user = new User(user_id,name,surname,password,usernameFromDB,dateOfBirthTolocalDate,age,type );
            }
            return user;
        }catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try { ps.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
    }

    @Override
    public User authenticate(String username, String password)  {
        password = encoder.encodeToString(password.getBytes());
        Connection connection = DaoFactory.getConnection();
        String sql = "select id,name,surname,password,username,age, dateOfBirth, type FROM Users WHERE username =? AND password = ?;";
        User user = null;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id") ;
                String passwordDecr = new String(decoder.decode(password.getBytes()));
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");
                String dateOfBirth = rs.getString("dateOfBirth");
                Type type = Type.valueOf(rs.getString("type"));
                LocalDate dateOfBirthTolocalDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                user = new User(id,name,surname,passwordDecr,username,dateOfBirthTolocalDate,age,type);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally {
            if (ps != null) {
                try { ps.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
        System.out.println(user.toString());
        return user;
    }

    @Override
    public ArrayList<User> getTeacherBySport(String sport) {
        Connection connection = DaoFactory.getConnection();
        User user = null;
        PreparedStatement ps =  null;
        String sql = "select id,name,surname,username FROM Users WHERE type = 'MAESTRO' AND sport = ?;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,sport);
            ResultSet rs = ps.executeQuery();
            ArrayList<User> usersList = new ArrayList<User>();
            while (rs.next()){
                int id = rs.getInt("id") ;
                String name = rs.getString("name");
                String surname = rs.getString("surname");

                // Create new user creator for simple teacher data for listing by sport?
                // Or maybe just give back an ArrayList with all the names of the teachers?
                user = new User(id, name, surname);
                usersList.add(user);
            }
            return usersList;
        }catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try { ps.close(); }
                catch (SQLException ignore) {}
            }
            if (connection != null) {
                try { connection.close(); }
                catch (SQLException ignore) {}
            }
        }
    }

}