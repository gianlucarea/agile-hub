package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;
import org.junit.jupiter.api.BeforeAll;
 import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    private static final UserDao userDao = new UserDaoImpl();
    Connection connection;

    Base64.Decoder decoder = Base64.getDecoder();

    @BeforeAll
     void setUp() throws SQLException {
        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("password");
        connection = daoFactory.getConnection();
        String sql1 = "DROP TABLE IF EXISTS agile_hub_test.Users; " ;
        String sql2 = "USE agile_hub_test; ";
        String createTable = "CREATE TABLE Users ( `id` int NOT NULL AUTO_INCREMENT,`name` varchar(45) NOT NULL,`surname` varchar(45) NOT NULL,`password` varchar(45) DEFAULT NULL,`username` varchar(45) NOT NULL,`dateOfBirth` varchar(45) NOT NULL, `age` int NOT NULL,`type` enum('NORMALE','ADMIN','SOCIO','SOCIO_PLUS','MAESTRO') NOT NULL,`sport` enum('CALCETTO','PALLAVOLO','NUOTO','TENNIS','PADEL') , PRIMARY KEY (`id`), UNIQUE KEY `id_user_UNIQUE` (`id`), UNIQUE KEY `username_UNIQUE` (`username`)) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

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
        User user = new User("Gianluca", "Rossi", "LamiaPassword!","GR", LocalDate.of(1997,9,26),  Type.SOCIO);

        userDao.registration(user);
        User userFromDb = userDao.getUserByUsername("GR");

        assertEquals("GR",userFromDb.getUsername());
        assertNotEquals("Gr",userFromDb.getUsername());
        assertEquals(LocalDate.of(1997,9,26),userFromDb.getDateOfBirth());
        assertEquals(25, userFromDb.getAge());
        assertNotEquals(24 ,userFromDb.getAge());

    }

    @Test
    void testRegistrationOfMaestro()  {
        User user = new User("Matteo", "Rossi", "LamiaPassword!","Maestro",LocalDate.of(1992,7,12), Type.MAESTRO, Sport.CALCETTO);
        userDao.registrationAdmin(user);

        User userFromDb = userDao.getUserByUsername("Maestro");

        assertEquals("Maestro",userFromDb.getUsername());
        assertNotEquals("maestro",userFromDb.getUsername());
        assertEquals(LocalDate.of(1992,7,12),userFromDb.getDateOfBirth());
        assertEquals(30, userFromDb.getAge());
        assertNotEquals(31 ,userFromDb.getAge());
    }


    @Test
    void authenticate()  {
        User userFromDb = userDao.authenticate("GR","LamiaPassword!");

        assertEquals("GR",userFromDb.getUsername());
        assertNotEquals("Gr",userFromDb.getUsername());
        assertEquals(LocalDate.of(1997,9,26),userFromDb.getDateOfBirth());
        assertEquals(25, userFromDb.getAge());
        assertNotEquals(24 ,userFromDb.getAge());
    }


    @Test
    void authenticateNull()  {
        User userFromDb = userDao.authenticate("","");
        assertNull(userFromDb);
    }

    @Test
    void authenticateVoidNull()  {
        User userFromDb = userDao.authenticate("WDSACS>AFCSXZDWXC`´´‹~¥‘“‘¥~‹÷‹~¥‘“","FACESCESXDCWSC AS2€¥Ω¥‹´``´´‹~¥‘“");
        assertNull(userFromDb);
    }

    @Test
    void getUserByIDTest(){
        User userFromDB = userDao.getUserById(1);
        assertEquals("GR",userFromDB.getUsername());
        assertNotEquals("Gr",userFromDB.getUsername());
        assertEquals(LocalDate.of(1997,9,26),userFromDB.getDateOfBirth());
        assertEquals(25, userFromDB.getAge());
        assertNotEquals(24 ,userFromDB.getAge());
    }

    @Test
    void getUserByIDTestNull(){
        User userFromDB = userDao.getUserById(0);
        assertNull(userFromDB);
    }

    @Test
    void getUserByUsername(){
        User userFromDB = userDao.getUserByUsername("GR");
        assertEquals("GR",userFromDB.getUsername());
        assertNotEquals("Gr",userFromDB.getUsername());
        assertEquals(LocalDate.of(1997,9,26),userFromDB.getDateOfBirth());
        assertEquals(25, userFromDB.getAge());
        assertNotEquals(24 ,userFromDB.getAge());
    }

    @Test
    void getUserByUsernameNull(){
        User userFromDB = userDao.getUserByUsername("");
        User userFromDB1 = userDao.getUserByUsername("DWYOUQDGWVOG)");

        assertNull(userFromDB);
        assertNull(userFromDB1);
    }

    @Test
    void getTeacherBySport(){
        ArrayList<User> usersFromDB = userDao.getTeacherBySport("CALCETTO");

        assertEquals(false, usersFromDB.isEmpty());
        assertEquals(User.class, usersFromDB.get(0).getClass());
    }

    @Test
    void getTeacherByWrongSport(){
        ArrayList<User> usersFromDB = userDao.getTeacherBySport("");
        ArrayList<User> usersFromDB1 = userDao.getTeacherBySport("PALLAVOLO");
        ArrayList<User> usersFromDB2 = userDao.getTeacherBySport("XYZ");


        assertEquals(true, usersFromDB.isEmpty());
        assertEquals(true, usersFromDB1.isEmpty());
        assertEquals(true, usersFromDB2.isEmpty());

    }
}
