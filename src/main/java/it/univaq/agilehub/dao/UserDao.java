package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.User;

import java.util.ArrayList;

public interface UserDao {

     void registration(User user);
     void registrationAdmin(User user);
     User getUserById(int id);
     User getUserByUsername(String username);
     User authenticate(String username, String password);
     public ArrayList<User> getTeacherBySport(String sport);
}
