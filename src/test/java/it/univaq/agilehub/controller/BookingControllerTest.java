package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.DaoFactory;
import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static it.univaq.agilehub.model.Sport.*;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingControllerTest {


    private static final DaoFactory daoFactory = new DaoFactory();
    static Connection connection;
    UserDao userDao = new UserDaoImpl();
    BookingController controller;

    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();

        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("password");
        connection = daoFactory.getConnection();

        try{
            ClassLoader classLoader = BookingControllerTest.class.getClassLoader();
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


    @BeforeEach
    public void start() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/viste/prenotazione.fxml"));
        AnchorPane pane = loader.load();
        FxToolkit.setupStage(stage -> {
            stage.setScene(new Scene(pane, 1000, 500));
            stage.show();
        });

        controller = loader.getController();

    }


    /**
     * @param robot - Will be injected by the test runner.
     */

    @Test
    void prenotazionUtenteNormaleTest(FxRobot robot) throws InterruptedException {
        User normale = userDao.getUserById(1);
        controller.setUser(normale);

        FxAssert.verifyThat("#selezioneCampo", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);
        FxAssert.verifyThat("#prenota", Node::isDisable);

        robot.clickOn("#selezioneTipologia").clickOn(String.valueOf(PALLAVOLO));

        FxAssert.verifyThat("#selezioneCampo", isEnabled());

        robot.clickOn("#data").write("10/06/2023");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        robot.clickOn("#numeroPartecipanti").write("12");
        robot.clickOn("#selezioneCampo").clickOn("Pallavolo 2");

        FxAssert.verifyThat("#selezioneOrario", isEnabled());
        robot.clickOn("#selezioneOrario").clickOn("19:00 - 20:00");

        FxAssert.verifyThat("#prenota", isEnabled());
        robot.clickOn("#prenota").clickOn();

        FxAssert.verifyThat("#bookingLabel", LabeledMatchers.hasText("Prenotazione efettuata!"));


    }

    @Test
    void prenotazionUtenteNormaleWrongTestData(FxRobot robot) throws InterruptedException {
        User normale = userDao.getUserById(1);
        controller.setUser(normale);

        FxAssert.verifyThat("#selezioneCampo", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);
        FxAssert.verifyThat("#prenota", Node::isDisable);

        robot.clickOn("#selezioneTipologia").clickOn(String.valueOf(PALLAVOLO));

        FxAssert.verifyThat("#selezioneCampo", isEnabled());

        robot.clickOn("#data").write("24/06/2023");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        robot.clickOn("#numeroPartecipanti").write("12");
        robot.clickOn("#selezioneCampo").clickOn("Pallavolo 2");

        FxAssert.verifyThat("#selezioneOrario", isEnabled());
        robot.clickOn("#selezioneOrario").clickOn("19:00 - 20:00");

        FxAssert.verifyThat("#prenota", isEnabled());
        robot.clickOn("#prenota").clickOn();

        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Errore controllare numero prenotati o data"));
    }

    @Test
    void prenotazionUtenteNormaleWrongTestPartecipanti(FxRobot robot) throws InterruptedException {
        User normale = userDao.getUserById(1);
        controller.setUser(normale);

        FxAssert.verifyThat("#selezioneCampo", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);
        FxAssert.verifyThat("#prenota", Node::isDisable);

        robot.clickOn("#selezioneTipologia").clickOn(String.valueOf(CALCETTO));

        FxAssert.verifyThat("#selezioneCampo", isEnabled());

        robot.clickOn("#data").write("09/06/2023");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        robot.clickOn("#numeroPartecipanti").write("13");
        robot.clickOn("#selezioneCampo").clickOn("Calcetto 1");

        FxAssert.verifyThat("#selezioneOrario", isEnabled());
        robot.clickOn("#selezioneOrario").clickOn("19:00 - 20:00");

        FxAssert.verifyThat("#prenota", isEnabled());
        robot.clickOn("#prenota").clickOn();

        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Errore controllare numero prenotati o data"));
    }
    @Test
    void prenotazionUtenteSocioTest(FxRobot robot) throws InterruptedException {
        User socio = userDao.getUserById(3);
        controller.setUser(socio);


        FxAssert.verifyThat("#selezioneCampo", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);
        FxAssert.verifyThat("#prenota", Node::isDisable);

        robot.clickOn("#selezioneTipologia").clickOn(String.valueOf(BASKET));

        FxAssert.verifyThat("#selezioneCampo", isEnabled());

        robot.clickOn("#data").write("08/06/2023");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        robot.clickOn("#numeroPartecipanti").write("10");
        robot.clickOn("#selezioneCampo").clickOn("Basket 1");

        FxAssert.verifyThat("#selezioneOrario", isEnabled());
        robot.clickOn("#selezioneOrario").clickOn("18:00 - 19:00");

        FxAssert.verifyThat("#prenota", isEnabled());
        robot.clickOn("#prenota").clickOn();
        FxAssert.verifyThat("#bookingLabel", LabeledMatchers.hasText("Prenotazione efettuata!"));


    }

    @Test
    void prenotazionUtenteSocioWrongTestData(FxRobot robot) throws InterruptedException {
        User socio = userDao.getUserById(3);
        controller.setUser(socio);

        FxAssert.verifyThat("#selezioneCampo", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);
        FxAssert.verifyThat("#prenota", Node::isDisable);

        robot.clickOn("#selezioneTipologia").clickOn(String.valueOf(CALCETTO));

        FxAssert.verifyThat("#selezioneCampo", isEnabled());

        robot.clickOn("#data").write("04/06/2023");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        robot.clickOn("#numeroPartecipanti").write("10");
        robot.clickOn("#selezioneCampo").clickOn("Calcetto 2");

        FxAssert.verifyThat("#selezioneOrario", isEnabled());

        robot.clickOn("#selezioneOrario").clickOn("19:00 - 20:00");

        FxAssert.verifyThat("#prenota", isEnabled());
        robot.clickOn("#prenota").clickOn();

        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Errore controllare numero prenotati o data"));
    }
    @Test
    void prenotazionUtenteSocioWrongTestPartecipanti(FxRobot robot) throws InterruptedException {
        User normale = userDao.getUserById(1);
        controller.setUser(normale);

        FxAssert.verifyThat("#selezioneCampo", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);
        FxAssert.verifyThat("#prenota", Node::isDisable);

        robot.clickOn("#selezioneTipologia").clickOn(String.valueOf(TENNIS));

        FxAssert.verifyThat("#selezioneCampo", isEnabled());

        robot.clickOn("#data").write("07/06/2023");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        robot.clickOn("#numeroPartecipanti").write("13");
        robot.clickOn("#selezioneCampo").clickOn("Tennis 1");

        FxAssert.verifyThat("#selezioneOrario", isEnabled());
        robot.clickOn("#selezioneOrario").clickOn("18:00 - 19:00");

        FxAssert.verifyThat("#prenota", isEnabled());
        robot.clickOn("#prenota").clickOn();

        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Errore controllare numero prenotati o data"));
    }
    @Test
    void prenotaButtonIsDisabled(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#prenota", Node::isDisable);
    }
}

