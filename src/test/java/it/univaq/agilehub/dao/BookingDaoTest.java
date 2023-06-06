package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Booking;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.utility.Utility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingDaoTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    BookingDao bookingDao = new BookingDaoImpl();
    Connection connection;

    @BeforeAll
    void setUp() throws Exception {
        //Database creation
        try {
            Utility.readScript();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
