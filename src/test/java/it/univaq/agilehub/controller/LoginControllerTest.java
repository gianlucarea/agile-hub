package it.univaq.agilehub.controller;

import it.univaq.agilehub.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerTest {


    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();
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

    /**
     * @param robot - Will be injected by the test runner.
     */

    @Test
    void testButtonClick(FxRobot robot) throws InterruptedException {
        robot.clickOn("#username").write("aldino");
        robot.clickOn("#password").write("password");

    }
}