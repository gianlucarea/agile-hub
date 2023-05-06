package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.User;

import java.sql.SQLException;

public interface UserDao {

    public boolean insertUser(User user);


    public User getUserById(int id) throws SQLException;


}
