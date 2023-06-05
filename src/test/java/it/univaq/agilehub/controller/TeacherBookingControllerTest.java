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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static it.univaq.agilehub.model.Sport.PALLAVOLO;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherBookingControllerTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    static Connection connection;

    UserDao userDao = new UserDaoImpl();

    TeacherReservationController controller;

    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();

        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("root");
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
                getClass().getResource("/viste/prenotaMaestri.fxml"));

        AnchorPane pane = loader.load();
        FxToolkit.setupStage(stage -> {
            stage.setScene(new Scene(pane, 1000, 500));
            stage.show();
        });

        controller = loader.getController();
    }

    @Test
    void TeacherBookingCompleteTest (FxRobot robot) throws Exception {
        User user = userDao.getUserById(5);
        controller.setUser(user);

        FxAssert.verifyThat("#prenotaMaestroButton", Node::isDisable);
        FxAssert.verifyThat("#dataPrenotazioneMaestro", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);

        robot.clickOn("#selezioneSport").clickOn(String.valueOf(PALLAVOLO));
        robot.clickOn("#listaMaestri").clickOn("Hector Barbossa");

        FxAssert.verifyThat("#dataPrenotazioneMaestro", isEnabled());

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        robot.clickOn("#dataPrenotazioneMaestro").write(tomorrow.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        FxAssert.verifyThat("#selezioneOrario", isEnabled());

        robot.clickOn("#selezioneOrario").clickOn("13:00 - 14:00");

        FxAssert.verifyThat("#prenotaMaestroButton", isEnabled());

        robot.clickOn("#prenotaMaestroButton");

        FxAssert.verifyThat("#confermaPrenotazioneMaestro", LabeledMatchers.hasText("Prenotazione effettuata"));
    }

    @Test
    void TeacherBookingAlreadyBookedForSameDateTest (FxRobot robot) throws Exception {
        User user = userDao.getUserById(6);
        controller.setUser(user);

        FxAssert.verifyThat("#prenotaMaestroButton", Node::isDisable);
        FxAssert.verifyThat("#dataPrenotazioneMaestro", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);

        robot.clickOn("#selezioneSport").clickOn(String.valueOf(PALLAVOLO));
        robot.clickOn("#listaMaestri").clickOn("Hector Barbossa");

        FxAssert.verifyThat("#dataPrenotazioneMaestro", isEnabled());

        LocalDate now = LocalDate.now();
        robot.clickOn("#dataPrenotazioneMaestro").write(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        FxAssert.verifyThat("#selezioneOrario", isEnabled());

        robot.clickOn("#selezioneOrario").clickOn("11:00 - 12:00");

        FxAssert.verifyThat("#prenotaMaestroButton", isEnabled());

        robot.clickOn("#prenotaMaestroButton");

        FxAssert.verifyThat("#confermaPrenotazioneMaestro", LabeledMatchers.hasText("Prenotazione effettuata"));

        FxAssert.verifyThat("#prenotaMaestroButton", Node::isDisable);

        robot.clickOn("#dataPrenotazioneMaestro").write(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        FxAssert.verifyThat("#selezioneOrario", isEnabled());

        robot.clickOn("#selezioneOrario").clickOn("10:00 - 11:00");

        FxAssert.verifyThat("#prenotaMaestroButton", isEnabled());

        robot.clickOn("#prenotaMaestroButton");

        FxAssert.verifyThat("#confermaPrenotazioneMaestro", LabeledMatchers.hasText("Prenotazione già effettuata\n Scegli un altra data"));
    }

    @Test
    void TeacherBookingNoAvailableBookingTest (FxRobot robot) throws Exception {
        int i = 1;
        LocalDate now = LocalDate.now();

        FxAssert.verifyThat("#prenotaMaestroButton", Node::isDisable);
        FxAssert.verifyThat("#dataPrenotazioneMaestro", Node::isDisable);
        FxAssert.verifyThat("#selezioneOrario", Node::isDisable);

        robot.clickOn("#selezioneSport").clickOn(String.valueOf(PALLAVOLO));
        robot.clickOn("#listaMaestri").clickOn("Hector Barbossa");

        FxAssert.verifyThat("#dataPrenotazioneMaestro", isEnabled());

        while (i < 3) {
            User user = userDao.getUserById(i);
            controller.setUser(user);

            robot.clickOn("#dataPrenotazioneMaestro").write(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

            FxAssert.verifyThat("#selezioneOrario", isEnabled());

            robot.clickOn("#selezioneOrario").clickOn("1" + (3+i) + ":00 - 1" + (4+i) + ":00");

            FxAssert.verifyThat("#prenotaMaestroButton", isEnabled());

            robot.clickOn("#prenotaMaestroButton");

            FxAssert.verifyThat("#confermaPrenotazioneMaestro", LabeledMatchers.hasText("Prenotazione effettuata"));

            i = i + 1;
        }

        User user = userDao.getUserById(4);
        controller.setUser(user);

        FxAssert.verifyThat("#dataPrenotazioneMaestro", isEnabled());

        robot.clickOn("#dataPrenotazioneMaestro").write(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        FxAssert.verifyThat("#selezioneOrario", isEnabled());

        robot.clickOn("#selezioneOrario").clickOn("18:00 - 19:00");

        FxAssert.verifyThat("#prenotaMaestroButton", isEnabled());

        robot.clickOn("#prenotaMaestroButton");

        FxAssert.verifyThat("#confermaPrenotazioneMaestro", LabeledMatchers.hasText("Prenotazioni piene\n Scegli un altra data"));
    }
}
