package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.DaoFactory;
import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.junit.jupiter.api.Test;

import javafx.scene.Scene;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static org.testfx.matcher.base.NodeMatchers.isEnabled;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IscrivitiControllerTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    static Connection connection;



    RegistrationController controller;

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
                getClass().getResource("/viste/registration.fxml"));
        AnchorPane pane = loader.load();
        FxToolkit.setupStage(stage -> {
            stage.setScene(new Scene(pane, 1000, 1000));
            stage.show();
        });
    }






   @Test
    void testButtonClick(FxRobot robot) throws InterruptedException {
        
        FxAssert.verifyThat("#avantiButton", Node::isDisable);

        robot.clickOn("#nome").write("usama");
        robot.clickOn("#cognome").write("lb");
        robot.clickOn("#password").write("password");
        robot.clickOn("#username").write("Usama ");
        robot.clickOn("#dataNascita").write("24/06/1998");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#avantiButton");

    }
    @Test
    void testButtonClickWrong(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);

        robot.clickOn("#nome").write("usahhhma");
        robot.clickOn("#cognome").write("lhhhb");
        robot.clickOn("#password").write("pasjjsword");
        robot.clickOn("#username").write("Usaggma ");
        robot.clickOn("#dataNascita").write("30/06/2024");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#avantiButton").clickOn();
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Errore nella data di nascita"));
    }
   @Test
    void testButtonBackClick(FxRobot robot) throws InterruptedException {
        robot.clickOn("#indietroButton").clickOn();
    }


    @Test
    void testButtonSocioPlusClick(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);

        robot.clickOn("#nome").write("Luigi");
        robot.clickOn("#cognome").write("Rossi");
        robot.clickOn("#password").write("password1");
        robot.clickOn("#username").write("Luigi ");
        robot.clickOn("#dataNascita").write("24/06/2003");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#socioPlusBox");
        robot.clickOn("#avantiButton");

    }

    @Test
    void testButtonSocioPlusClickWrong(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);

        robot.clickOn("#nome").write("Luigi");
        robot.clickOn("#cognome").write("Rossi");
        robot.clickOn("#password").write("password1");
        robot.clickOn("#username").write("Luigi ");
        robot.clickOn("#dataNascita").write("24/06/2025");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#socioPlusBox");
        robot.clickOn("#avantiButton");
    }
   @Test
    void testButtonSocioClick(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);

        robot.clickOn("#nome").write("Mario");
        robot.clickOn("#cognome").write("Rossi");
        robot.clickOn("#password").write("password2");
        robot.clickOn("#username").write("Mario ");
        robot.clickOn("#dataNascita").write("24/06/2001");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#socioBox");
        robot.clickOn("#avantiButton");
    }

}

