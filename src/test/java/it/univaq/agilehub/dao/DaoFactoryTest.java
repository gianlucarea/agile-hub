package it.univaq.agilehub.dao;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class DaoFactoryTest {

    private static final DaoFactory daoFactory = new DaoFactory();

    @BeforeEach
    void setUp(){
        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("password");
    }

    @Test
    void getConnectionIsTestDb(){
        try {
           Connection connection = daoFactory.getConnection();
           assertEquals("agile_hub_test", connection.getCatalog());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getConnectionIsNotProductionDb(){
        try {
            Connection connection = daoFactory.getConnection();
            assertNotEquals("agile_hub_production",connection.getCatalog());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
