package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class UserDaoTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    private static final UserDao userDao = new UserDaoImpl();
    Connection connection;

    @BeforeEach
    void setUp(){
        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("password");
        connection = daoFactory.getConnection();
    }

    @Test
    void testRegistrationOfUser()  {
        User user = new User("Mario", "Rossi", "LamiaPassword!","MRXX",30, Type.NORMALE);
        userDao.registration(user);
        User userFromDb = userDao.getUserByUsername("MRX");
        assertEquals("TGFtaWFQYXNzd29yZCE=",userFromDb.getPassword());
    }


}
