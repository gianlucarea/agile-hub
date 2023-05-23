package it.univaq.agilehub.model;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {
    User user = new User();

  @Test
  void testToCheck () {
      assertEquals("Gianluca Rea",user.toCheck("Gianluca","Rea"));
  }

    //This test suppose to fail to show
    @Test
    void testToCheck2 () {
        assertNotEquals("GianlucaRea",user.toCheck("Gianluca","Rea"));
    }

    /*

    @Test
    void ageCalculatorEqual25() {
        String age = "26/09/1997";
        try {
            assertEquals(25, user.ageCalculator(age));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void ageCalculatorNotEqual25() {
        String age = "26/09/1999";
        try {
            assertNotEquals(25, user.ageCalculator(age));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    */
    @Test
    void dateConverterRight() {
        String date = "1997-09-26";
        assertEquals("26/09/1997", User.dateOfBirthConverter(date));
    }

    @Test
    void dateConverterWrong() {
        String date = "1997-09-26";
        assertNotEquals("1997/09/26", User.dateOfBirthConverter(date));
    }
}