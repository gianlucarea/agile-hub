package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.TeacherBooking;
import it.univaq.agilehub.model.TimeSlot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherBookingDaoTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    TeacherBookingDao teacherBookingDao = new TeacherBookingDaoImpl();
    Connection connection;

    @BeforeAll
    void setUp() throws SQLException {
        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("password");
        connection = daoFactory.getConnection();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File scriptFile = new File(classLoader.getResource("Testing_DB.sql").getFile());
            if (scriptFile.exists()) {
                var buffer = new StringBuilder();
                var scanner = new Scanner(scriptFile);
                while (scanner.hasNextLine()) {
                    var line = scanner.nextLine();
                    buffer.append(line);
                    if (line.endsWith(";")) {
                        String command = buffer.toString();
                        connection.createStatement().execute(command);
                        buffer = new StringBuilder();
                    } else {
                        buffer.append("\n");
                    }
                }
            } else System.err.println("File not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) connection.close();
        }
    }

    @Test
    void getTeacherBookingByIDTest() {
        int id = 1;
        try {
            TeacherBooking t = teacherBookingDao.getTeacherBookingById(id);
            assertNotNull(t);
            assertEquals(true, t.getTeacherId() > 0);
            assertEquals(true, t.getUserId() > 0);

        } catch (SQLException e) {

        }
    }

    @Test
    void getTeacherBookingByIDTestNull() {
        int id = 0;
        try {
            TeacherBooking t1 = teacherBookingDao.getTeacherBookingById(id);
            assertNull(t1);
        } catch (SQLException e) {

        }
    }

    @Test
    void insertTeacherBookingTest() {
        TeacherBooking t = new TeacherBooking(1, 7, LocalDate.now(), Sport.CALCETTO);
        try {
            int x = teacherBookingDao.insertTeacherBooking(t);
            assertEquals(true, x > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void insertTeacherBookingTestInvalid() {
        TeacherBooking t1 = new TeacherBooking(0, 7, LocalDate.now(), Sport.CALCETTO);
        TeacherBooking t2 = new TeacherBooking(1, -1, LocalDate.now(), Sport.CALCETTO);
        TeacherBooking t3 = new TeacherBooking(1, -1, LocalDate.now().minusDays(10), Sport.CALCETTO);

        assertThrows(NullPointerException.class, () -> {
            teacherBookingDao.insertTeacherBooking(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            teacherBookingDao.insertTeacherBooking(t1);
            teacherBookingDao.insertTeacherBooking(t2);
            teacherBookingDao.insertTeacherBooking(t3);

        });

    }

    @Test
    void insertTimeTeacherBookingTest() {
        assertDoesNotThrow(() -> {
            teacherBookingDao.insertTimeTeacherBooking(7, 1, "25/09/2023", 3);
        });
    }

    @Test
    void insertTimeTeacherBookingTestInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            teacherBookingDao.insertTimeTeacherBooking(7, 0, "25/09/2023", 3);
            teacherBookingDao.insertTimeTeacherBooking(-700, 1, "25/09/2023", 3);
            teacherBookingDao.insertTimeTeacherBooking(7, 1, "25/09/2000", 3);
            teacherBookingDao.insertTimeTeacherBooking(7, 1, "25/09/2023", 0);
        });
    }

    @Test
    void insertTimeTeacherBookingTestInvalidDateFormat() {
        assertThrows(DateTimeParseException.class, () -> {
            teacherBookingDao.insertTimeTeacherBooking(7, 1, "25/09/202322", 3);
            teacherBookingDao.insertTimeTeacherBooking(7, 1, "25-09-2023", 3);
            teacherBookingDao.insertTimeTeacherBooking(7, 1, "25-093-2023", 3);
            teacherBookingDao.insertTimeTeacherBooking(7, 1, "250-09-2023", 3);
            teacherBookingDao.insertTimeTeacherBooking(7, 1, "00-09-2023", 3);
        });
    }

    @Test
    void getTimeTeacherBookingTest() {
        TimeSlot timeSlot = teacherBookingDao.getTimeTeacherBooking(1);
        assertNotNull(timeSlot);
        assertEquals(true, timeSlot.getId() > 0);
        assertEquals(true, !timeSlot.getTime_slot().isEmpty());
        assertEquals(false, timeSlot.getId() <= 0);
        assertEquals(false, timeSlot.getTime_slot().isEmpty());
    }

    @Test
    void getTimeTeacherBookingInvalidTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            teacherBookingDao.getTimeTeacherBooking(-1);
            teacherBookingDao.getTimeTeacherBooking(0);
        });
    }

    @Test
    void getAllTeacherBookingsForMaestroTest() {
        try {
            int teacher_id = 7;
            List<TeacherBooking> teacherBookings = teacherBookingDao.getAllTeacherBookingsForMaestro(teacher_id);
            assertNotNull(teacherBookings);
            assertEquals(false, teacherBookings.isEmpty());
            for (TeacherBooking t : teacherBookings) {
                assertNotNull(t);
                assertEquals(true, t.getId() > 0);
                assertEquals(true, t.getUserId() > 0);
                assertEquals(true, t.getTeacherId() == teacher_id);
                assertEquals(false, t.getId() <= 0);
                assertEquals(false, t.getUserId() <= 0);
                assertEquals(false, t.getTeacherId() != teacher_id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllTeacherBookingsForMaestroInvalidTest() {

        int teacher_id = 0;
        int teacher_id2 = -10;
        assertThrows(IllegalArgumentException.class, () -> {
            teacherBookingDao.getAllTeacherBookingsForMaestro(teacher_id);
            teacherBookingDao.getAllTeacherBookingsForMaestro(teacher_id2);
        });
    }

    @Test
    void doesTeacherBookingAlreadyExistTest(){
        TeacherBooking t = new TeacherBooking(1,7,LocalDate.of(2023,05,30),Sport.CALCETTO);
        TeacherBooking f = new TeacherBooking(1,7,LocalDate.of(2023,05,20),Sport.CALCETTO);

        try {

            boolean x = teacherBookingDao.doesTeacherBookingAlreadyExist(t);
            boolean y = teacherBookingDao.doesTeacherBookingAlreadyExist(f);
            assertTrue(x);
            assertFalse(y);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void doesTeacherBookingAlreadyExistInvalidTest(){
        TeacherBooking t1 = new TeacherBooking(1,7,LocalDate.of(2023,05,30),Sport.CALCETTO);
        TeacherBooking t2 = new TeacherBooking(-1,0,LocalDate.of(2023,05,20),Sport.CALCETTO);

        assertThrows(NullPointerException.class, () -> {
            teacherBookingDao.doesTeacherBookingAlreadyExist(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            teacherBookingDao.doesTeacherBookingAlreadyExist(t1);
            teacherBookingDao.doesTeacherBookingAlreadyExist(t2);
        });
    }

    @Test
    void isTeacherBookingFullTest(){
        int teacherID1 = 7;
        int teacherID2 = 8;
        String bookingDate = "30/05/2023";

        try {
            boolean t1 = teacherBookingDao.isTeacherBookingFull(teacherID1,bookingDate);
            boolean t2 = teacherBookingDao.isTeacherBookingFull(teacherID2,bookingDate);
            assertTrue(t1);
            assertFalse(t2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isTeacherBookingFullWrongTeacherIDTest(){
        int teacherID1 = 0;
        int teacherID2 = -8;
        String bookingDate = "30/05/2023";

        assertThrows(IllegalArgumentException.class, () -> {
            teacherBookingDao.isTeacherBookingFull(teacherID1,bookingDate);
            teacherBookingDao.isTeacherBookingFull(teacherID2,bookingDate);
        });
    }

    @Test
    void isTeacherBookingFullWrongDateFormatTest() {
        int teacherID1 = 7;

        assertThrows(DateTimeParseException.class, () -> {

            teacherBookingDao.isTeacherBookingFull(teacherID1,"00-09-2023");
            teacherBookingDao.isTeacherBookingFull(teacherID1,"250-09-2023");
            teacherBookingDao.isTeacherBookingFull(teacherID1,"25-093-2023");
            teacherBookingDao.isTeacherBookingFull(teacherID1,"25-09-2023");
            teacherBookingDao.isTeacherBookingFull(teacherID1,  "25/09/202322");
        });
    }
}