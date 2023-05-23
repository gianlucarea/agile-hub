package it.univaq.agilehub.utility;

public class Utility {

    /**
     *   yyyy-mm-dd TO dd/mm/yyyy
     */
    public static String dateOfBirthConverter(String paramFromFX){
        String dateOfBirth = "";
        String[] arrOfSplit = paramFromFX.split("-");
        for (int i = arrOfSplit.length - 1 ; i > -1; i--){
            dateOfBirth += arrOfSplit[i] + "/";
        }
        dateOfBirth = (dateOfBirth.substring(0, dateOfBirth.length() - 1));
        return dateOfBirth;
    }
}
