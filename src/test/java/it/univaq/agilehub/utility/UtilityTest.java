package it.univaq.agilehub.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {

    @Test
    void dateOfBirthConverterEquals(){
        String date = "1997-09-26";
        assertEquals("26/09/1997", Utility.dateOfBirthConverter(date));
    }

    @Test
    void dateOfBirthConverterNotEquals(){
        String date = "1997-09-26";
        assertNotEquals("26-09-1997", Utility.dateOfBirthConverter(date));
    }

    @Test
    void dateOfBirthConverterNull(){
        String date = null;
        assertThrows(NullPointerException.class , ()-> Utility.dateOfBirthConverter(date));
    }

    @Test
    void dateOfBirthConverterEmpty(){
        String date = "";
        assertEquals( "", Utility.dateOfBirthConverter(date));
    }
}
