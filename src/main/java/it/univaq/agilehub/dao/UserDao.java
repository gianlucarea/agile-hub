package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.User;

import java.sql.SQLException;

public interface UserDao {

    public boolean registration(User user);


    public User getUserById(int id) throws SQLException;
    public User getUserByUsername(String username) ;
    public User authenticate(String username, String password);

}
