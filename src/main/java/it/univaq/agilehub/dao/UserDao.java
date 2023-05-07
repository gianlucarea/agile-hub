package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.User;

import java.sql.SQLException;

public interface UserDao {

     boolean registration(User user);

     User getUserById(int id) throws SQLException;
     User getUserByUsername(String username) ;
     User authenticate(String username, String password);

}
