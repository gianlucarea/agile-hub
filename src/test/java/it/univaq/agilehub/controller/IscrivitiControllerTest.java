package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.DaoFactory;
import it.univaq.agilehub.utility.Utility;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
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
public class IscrivitiControllerTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    static Connection connection;


    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();

        //Database creation
        Utility.readScript();

    }

    @BeforeEach
    public void start() throws Exception {

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
        FxRobot robot = new FxRobot();
        robot.clickOn("#iscrivitiButton");
    }

   @Test
    void testButtonClick(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);
        robot.clickOn("#nome").write("usama");
        robot.clickOn("#cognome").write("lb");
        robot.clickOn("#username").write("Usama ");
        robot.clickOn("#password").write("password");
        robot.clickOn("#dataNascita").write("24/06/1998");

       FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#avantiButton");
    }

    @Test
    void testButtonClickWrong(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);
        robot.clickOn("#nome").write("usahhhma");
        robot.clickOn("#cognome").write("lhhhb");
        robot.clickOn("#username").write("Usaggma ");
        robot.clickOn("#password").write("pasjjsword");
        robot.clickOn("#dataNascita").write("30/06/2024");

        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#avantiButton").clickOn();
        FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Errore nella data di nascita"));
    }

   @Test
    void testButtonBackClick(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#indietroButton", isEnabled());
        robot.clickOn("#indietroButton").clickOn();
   }


    @Test
    void testButtonSocioPlusClick(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);
        robot.clickOn("#nome").write("Luigi");
        robot.clickOn("#cognome").write("Rossi");
        robot.clickOn("#username").write("Luigi");
        robot.clickOn("#password").write("password1");
        robot.clickOn("#dataNascita").write("24/06/2003");
        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#socioPlusBox");
        robot.clickOn("#avantiButton");

    }

    @Test
    void testButtonSocioPlusClickWrong(FxRobot robot)  {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);
        robot.clickOn("#nome").write("Luigi");
        robot.clickOn("#cognome").write("Rossi");
        robot.clickOn("#username").write("Luigi ");
        robot.clickOn("#password").write("password1");
        robot.clickOn("#dataNascita").write("24/06/2025");

        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#socioPlusBox");
        robot.clickOn("#avantiButton");
    }

   @Test
    void testButtonSocioClick(FxRobot robot) throws InterruptedException {
        FxAssert.verifyThat("#avantiButton", Node::isDisable);
        robot.clickOn("#nome").write("Mario");
        robot.clickOn("#cognome").write("Rossi");
        robot.clickOn("#username").write("Mario ");
        robot.clickOn("#password").write("password2");
        robot.clickOn("#dataNascita").write("24/06/2001");

       FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#socioBox");
        robot.clickOn("#avantiButton");
    }

}

