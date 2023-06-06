package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.DaoFactory;
import it.univaq.agilehub.utility.Utility;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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

import static it.univaq.agilehub.model.Sport.PALLAVOLO;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TeacherRegistrationControllerTest {

    private static final DaoFactory daoFactory = new DaoFactory();
    static Connection connection;

    AdminController controller;

    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();

        //Database creation
        Utility.readScript();

    }

    @BeforeEach
    public void start() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/viste/registraMaestri.fxml"));

        AnchorPane pane = loader.load();
        FxToolkit.setupStage(stage -> {
            stage.setScene(new Scene(pane, 1000, 500));
            stage.show();
        });

        controller = loader.getController();
    }

    @Test
    void TeacherRegistrationCompleteTest (FxRobot robot) throws InterruptedException{

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);

        robot.clickOn("#nomeMaesto").write("Antonio");
        robot.clickOn("#cognomeMaestro").write("Devastatore");
        robot.clickOn("#usernameMaestro").write("antodeva");
        robot.clickOn("#passwordMaestro").write("password");
        robot.clickOn("#dataNascitaMaestro").write("21/04/1984");
        robot.clickOn("#sportMaestro").clickOn(String.valueOf(PALLAVOLO));

        //FxAssert.verifyThat("#avantiMaestroButton", isEnabled());

        robot.clickOn("#avantiMaestroButton");

        //FxAssert.verifyThat("#confermaRegistrazioneMaestro", LabeledMatchers.hasText("Registrazione confermata"));
        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);
    }

    @Test
    void TeacherRegistrationNoNameTest (FxRobot robot) throws InterruptedException{

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);

        robot.clickOn("#cognomeMaestro").write("Devastatore");
        robot.clickOn("#usernameMaestro").write("antodeva");
        robot.clickOn("#passwordMaestro").write("password");
        robot.clickOn("#dataNascitaMaestro").write("21/04/1984");

        robot.clickOn("#sportMaestro").clickOn(String.valueOf(PALLAVOLO));

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);
    }

    @Test
    void TeacherRegistrationNoSurnameTest (FxRobot robot) throws InterruptedException{

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);

        robot.clickOn("#nomeMaesto").write("Antonio");
        robot.clickOn("#usernameMaestro").write("antodeva");
        robot.clickOn("#passwordMaestro").write("password");
        robot.clickOn("#dataNascitaMaestro").write("21/04/1984");

        robot.clickOn("#sportMaestro").clickOn(String.valueOf(PALLAVOLO));

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);
    }

    @Test
    void TeacherRegistrationNoUsernameTest (FxRobot robot) throws InterruptedException{

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);

        robot.clickOn("#nomeMaesto").write("Antonio");
        robot.clickOn("#cognomeMaestro").write("Devastatore");
        robot.clickOn("#passwordMaestro").write("password");
        robot.clickOn("#dataNascitaMaestro").write("21/04/1984");
        robot.clickOn("#sportMaestro").clickOn(String.valueOf(PALLAVOLO));

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);
    }

    @Test
    void TeacherRegistrationNoPasswordTest (FxRobot robot) throws InterruptedException{

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);

        robot.clickOn("#nomeMaesto").write("Antonio");
        robot.clickOn("#cognomeMaestro").write("Devastatore");
        robot.clickOn("#usernameMaestro").write("antodeva");
        robot.clickOn("#dataNascitaMaestro").write("21/04/1984");
        robot.clickOn("#sportMaestro").clickOn(String.valueOf(PALLAVOLO));

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);
    }

    @Test
    void TeacherRegistrationNoDateTest (FxRobot robot) throws InterruptedException{

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);

        robot.clickOn("#nomeMaesto").write("Antonio");
        robot.clickOn("#cognomeMaestro").write("Devastatore");
        robot.clickOn("#usernameMaestro").write("antodeva");
        robot.clickOn("#passwordMaestro").write("password");
        robot.clickOn("#sportMaestro").clickOn(String.valueOf(PALLAVOLO));

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);
    }

    @Test
    void TeacherRegistrationNoSportTest (FxRobot robot) throws InterruptedException{

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);

        robot.clickOn("#nomeMaesto").write("Antonio");
        robot.clickOn("#cognomeMaestro").write("Devastatore");
        robot.clickOn("#usernameMaestro").write("antodeva");
        robot.clickOn("#passwordMaestro").write("password");
        robot.clickOn("#dataNascitaMaestro").write("21/04/1984");

        FxAssert.verifyThat("#avantiMaestroButton", Node::isDisable);
    }
}
