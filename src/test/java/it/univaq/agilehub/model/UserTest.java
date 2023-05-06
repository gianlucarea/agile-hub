package it.univaq.agilehub.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UserTest {

  @Test
  void testToCheck () {
      User user = new User();
      assertEquals("Gianluca Rea",user.toCheck("Gianluca","Rea"));
  }

    //This test suppose to fail to show
    @Test
    void testToCheck2 () {
        User user = new User();
        assertNotEquals("GianlucaRea",user.toCheck("Gianluca","Rea"));
    }
}