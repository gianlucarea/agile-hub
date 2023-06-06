package it.univaq.agilehub.utility;

import java.io.*;
import java.sql.Connection;
import it.univaq.agilehub.dao.DaoFactory;
import org.apache.ibatis.jdbc.ScriptRunner;

public class Utility {

    private static final DaoFactory daoFactory = new DaoFactory();
    static Connection connection;

    /**
     *   yyyy-mm-dd TO dd/mm/yyyy
     */
    public static String dateOfBirthConverter(String paramFromFX){
        try{
        String dateOfBirth = "";
        String[] arrOfSplit = paramFromFX.split("-");
        for (int i = arrOfSplit.length - 1 ; i > -1; i--){
            dateOfBirth += arrOfSplit[i] + "/";
        }
        dateOfBirth = (dateOfBirth.substring(0, dateOfBirth.length() - 1));
            return dateOfBirth;
        } catch (NullPointerException e){
            throw e;
        }
    }


        public static void readScript() throws Exception {

            daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
            daoFactory.setUser("root");
            daoFactory.setPassword("password");
            connection = daoFactory.getConnection();

            System.out.println("Connection established......");
            //Initialize the script runner
            ScriptRunner sr = new ScriptRunner(connection);
            //Creating a reader object
            Reader reader = new BufferedReader(new FileReader(Utility.class.getResource("/Testing_DB.sql").getFile()));
            //Running the script
            sr.runScript(reader);
        }

}
