package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Pitch;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PitchDaoTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    PitchDao pitchDao = new PitchDaoImpl();
    Connection connection;

    @BeforeAll
    void setUp() throws SQLException {
        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("password");
        connection = daoFactory.getConnection();
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            File scriptFile = new File(classLoader.getResource("Testing_DB.sql").getFile());
            if(scriptFile.exists()) {
                var buffer = new StringBuilder();
                var scanner = new Scanner(scriptFile);
                while(scanner.hasNextLine()) {
                    var line = scanner.nextLine();
                    buffer.append(line);
                    if(line.endsWith(";")) {
                        String command = buffer.toString();
                        connection.createStatement().execute(command);
                        buffer = new StringBuilder();
                    } else {
                        buffer.append("\n");
                    }
                }
            }
            else System.err.println("File not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(connection != null) connection.close();
        }
    }

    @Test
    void getPitchBySportTest()   {
        List<Pitch> pitch ;
        try {
          pitch = pitchDao.getPitchBySport("CALCETTO");
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }

        assertEquals(false, pitch.isEmpty());
        assertEquals(true,pitch.size()>0);

    }

    @Test
    void getPitchBySportTestNull()   {
        List<Pitch> pitch;
        List<Pitch> pitch2;
        List<Pitch> pitch3;
        try {
            pitch = pitchDao.getPitchBySport("");
            pitch2 = pitchDao.getPitchBySport("£$%&/()))=");
            pitch3 = pitchDao.getPitchBySport("PALLAMANO");

        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }

        assertEquals(true, pitch.isEmpty());
        assertEquals(false,pitch.size()>0);
        assertEquals(true,pitch2.isEmpty());
        assertEquals(false,pitch2.size()>0);
        assertEquals(true,pitch3.isEmpty());
        assertEquals(false,pitch3.size()>0);

    }
}
