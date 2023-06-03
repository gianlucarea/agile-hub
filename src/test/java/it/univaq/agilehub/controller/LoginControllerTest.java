package it.univaq.agilehub.controller;

import it.univaq.agilehub.HelloApplication;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

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
    void testButtonClick(FxRobot robot) throws InterruptedException {
        Assertions.assertFalse(accediButton).hasText("clicked!");
        robot.clickOn("#username").write("aldino");
        robot.clickOn("#password").write("password");
        robot.clickOn("#accediButton");






    }
}