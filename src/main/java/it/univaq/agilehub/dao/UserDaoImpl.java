package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao{

    private static final String insertUser ="insert into agilehub.user value (?)";

    @Override
    public boolean insertUser(User user) {
        return false;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        Connection connection = DaoFactory.getConnection();
        User user = null;

        String sql = "select id,name,surname,password,username,age,type FROM Users WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
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

}
