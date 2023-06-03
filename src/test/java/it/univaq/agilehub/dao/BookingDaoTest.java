package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Booking;
import it.univaq.agilehub.model.Sport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingDaoTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    BookingDao bookingDao = new BookingDaoImpl();
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
    void insertBookingTest() {
        Booking booking = new Booking(6, LocalDate.now(), 10,  Sport.CALCETTO);

        int id = bookingDao.insertBooking(booking);
        assertNotEquals(0,id);
    }

    @Test
    void insertBookingTestWrongBooking() {
        Booking booking = new Booking(0, LocalDate.now(), 10,  Sport.CALCETTO);
        Booking booking2 = new Booking(1, LocalDate.now().minusDays(3), 10,  Sport.CALCETTO);

        assertThrows(NullPointerException.class, () -> {
            bookingDao.insertBooking(null);
        });

        assertThrows(IllegalArgumentException.class,() ->{
            bookingDao.insertBooking(booking);
            bookingDao.insertBooking(booking2);
        });
    }

    @Test
    void insertTimeBookingTest(){
        assertDoesNotThrow(()->{
            bookingDao.insertTimeBooking(1,1,LocalDate.now().toString(),5);
        });

    }

    @Test
    void insertTimeBookingInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookingDao.insertTimeBooking(0, 1, LocalDate.now().toString(), 2);
            bookingDao.insertTimeBooking(1, -1, LocalDate.now().toString(), 3);
            bookingDao.insertTimeBooking(1, 0, LocalDate.now().toString(), 0);
            bookingDao.insertTimeBooking(1, 1, LocalDate.now().minusDays(100).toString(), 1);

        });
    }
        @Test
        void insertTimeBookingInvalidDateFormat() {
            assertThrows(DateTimeParseException.class, () -> {
                bookingDao.insertTimeBooking(1, 1, "25/09/202322", 3);
                bookingDao.insertTimeBooking(1, 1, "25-09-2023", 3);
                bookingDao.insertTimeBooking(1, 1, "25-093-2023", 3);
                bookingDao.insertTimeBooking(1, 1, "250-09-2023", 3);
                bookingDao.insertTimeBooking(1, 1, "00-09-2023", 3);
            });
        }
}
