package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    private static final UserDao userDao = new UserDaoImpl();
    Connection connection;

    @BeforeAll
     void setUp() throws SQLException {
        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("password");
        connection = daoFactory.getConnection();
        String sql1 = "DROP TABLE IF EXISTS agile_hub_test.Users; " ;
        String sql2 = "USE agile_hub_test; ";
        String createTable = "CREATE TABLE Users ( `id` int NOT NULL AUTO_INCREMENT,`name` varchar(45) NOT NULL,`surname` varchar(45) NOT NULL,`password` varchar(45) DEFAULT NULL,`username` varchar(45) NOT NULL, `age` int NOT NULL,`type` enum('NORMALE','ADMIN','SOCIO','SOCIO_PLUS','MAESTRO') NOT NULL, PRIMARY KEY (`id`), UNIQUE KEY `id_user_UNIQUE` (`id`), UNIQUE KEY `username_UNIQUE` (`username`)) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";


        // Trasform in a for
        PreparedStatement pst  = connection.prepareStatement(sql1);
        pst.executeUpdate();
        pst  = connection.prepareStatement( sql2);
        pst.executeUpdate();
        pst  = connection.prepareStatement( createTable);
        pst.executeUpdate();

    }

    @Test
    void testRegistrationOfUser()  {
        User user = new User("Gianluca", "Rossi", "LamiaPassword!","GR",30, Type.SOCIO);
        userDao.registration(user);
        User userFromDb = userDao.getUserByUsername("GR");
        assertEquals("TGFtaWFQYXNzd29yZCE=",userFromDb.getPassword());
    }

    @Test
    void testGetUserUsername()  {
        User user = userDao.getUserByUsername("GR");
        assertEquals("Gianluca Rossi", user.getName() + " " + user.getSurname());
    }


}
