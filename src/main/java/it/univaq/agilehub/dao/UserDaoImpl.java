package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.*;


public class UserDaoImpl implements UserDao{
    Encoder encoder = Base64.getEncoder();
    Decoder decoder = Base64.getDecoder();
    private static final String insertUser ="insert into agilehub.user value (?)";

    @Override
    public boolean registration(User user) {
        Connection connection = DaoFactory.getConnection();
        String sql = "INSERT INTO Users (name,surname,password,username,age,type) VALUES (?,?,?,?,?,?)";
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, user.getName());
            pst.setString(2, user.getSurname());
            pst.setString(3, encoder.encodeToString(user.getPassword().getBytes()));
            pst.setString(4, user.getUsername());
            pst.setInt(5,user.getAge());
            pst.setString(6,user.getType().toString());
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
        String sql = "select id,name,surname,password,username,age,type FROM Users WHERE id = ?;";
        ps = connection.prepareStatement(sql);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
                int user_id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String password = rs.getString("password");
                String username = rs.getString("username");
                int age = rs.getInt("age");
                Type type = Enum.valueOf(Type.class , rs.getString("type")) ;
                System.out.println(type);
                user = new User(user_id,name,surname,password,username,age,type );
            }
            return user;


    }

    @Override
    public User getUserByUsername(String username)  {
        Connection connection = DaoFactory.getConnection();
        User user = null;
        PreparedStatement ps =  null;
        String sql = "select id,name,surname,password,username,age,type FROM Users WHERE username = ?;";
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
                int age = rs.getInt("age");
                Type type = Enum.valueOf(Type.class , rs.getString("type")) ;
                user = new User(user_id,name,surname,password,usernameFromDB,age,type );
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
        String sql = "select id,name,surname,password,username,age,type FROM Users WHERE username =? AND password = ?;";
        User user = new User();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user.setId(rs.getInt("id"));
                user.setUsername(username);
                user.setPassword( new String(decoder.decode(password.getBytes())));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setAge(rs.getInt("age"));
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

}