package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.DaoFactory;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

import static org.testfx.matcher.base.NodeMatchers.isEnabled;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    Connection connection;
    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @BeforeEach
    public void start() throws Exception {

        daoFactory.setUrl("jdbc:mysql://localhost:3306/agile_hub_test");
        daoFactory.setUser("root");
        daoFactory.setPassword("root");
        connection = daoFactory.getConnection();

        try{
            ClassLoader classLoader = getClass().getClassLoader();
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

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/viste/login.fxml"));

        FxToolkit.setupStage(stage -> {
            ViewDispatcher dispatcher = ViewDispatcher.getInstance();
            try {
                dispatcher.loginView(stage);
            } catch (ViewException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     * @param robot - Will be injected by the test runner.
     */

    @Test
    void loginTest(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#accediButton", Node::isDisable);
        robot.clickOn("#username").write("univaq");
        robot.clickOn("#password").write("password");
        FxAssert.verifyThat("#accediButton", isEnabled());
        robot.clickOn("#accediButton");
    }
    @Test
    void iscrivitiTest(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#iscrivitiButton",isEnabled());
        robot.clickOn("#iscrivitiButton");
    }

    @Test
    void loginWrongUsernameAndPassword(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#accediButton", Node::isDisable);
        robot.clickOn("#username").write("aldino111w");
        robot.clickOn("#password").write("passwordewqawdfaw");
        FxAssert.verifyThat("#accediButton", isEnabled());
        robot.clickOn("#accediButton");

        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Username o password errati!"));
    }

    @Test
    void loginWrongUsername(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#accediButton", Node::isDisable);
        robot.clickOn("#username").write("aldino111w");
        robot.clickOn("#password").write("password");
        FxAssert.verifyThat("#accediButton", isEnabled());
        robot.clickOn("#accediButton");

        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Username o password errati!"));
    }
    @Test
    void loginWrongPassword(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#accediButton", Node::isDisable);
        robot.clickOn("#username").write("aldino");
        robot.clickOn("#password").write("passwordewqawdfaw");
        FxAssert.verifyThat("#accediButton", isEnabled());
        robot.clickOn("#accediButton");
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Username o password errati!"));
    }

    @Test
    void accediButtonIsDisabled(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#accediButton", Node::isDisable);
    }


}