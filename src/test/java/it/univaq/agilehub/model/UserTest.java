package it.univaq.agilehub.model;

import javafx.util.converter.LocalDateStringConverter;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {
    User user = new User();

    @Test
    void ageCalculatorEqual25() {
        LocalDate age = LocalDate.of(1997,9,26);
        try {
            assertEquals(25, user.ageCalculator(age));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void ageCalculatorNotEqual25() {
        LocalDate age = LocalDate.of(1999,9,26);
        try {
            assertNotEquals(25, user.ageCalculator(age));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void ageCalculatorNullParameter() {
        LocalDate age = null;
        assertThrows(NullPointerException.class , ()-> user.ageCalculator(age));
    }

    /*
    @Test
    void dateConverterRight() {
        String date = "1997-09-26";
        assertEquals("26/09/1997", User.dateOfBirthConverter(date));
    }

    @Test
    void dateConverterWrong() {
        String date = "1997-09-26";
        assertNotEquals("1997/09/26", User.dateOfBirthConverter(date));
    } */
}