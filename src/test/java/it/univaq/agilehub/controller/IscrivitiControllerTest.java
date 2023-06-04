package it.univaq.agilehub.controller;

import javafx.fxml.FXMLLoader;
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

import static org.testfx.matcher.base.NodeMatchers.isEnabled;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IscrivitiControllerTest {

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
   @Test
    void testButtonClick(FxRobot robot) throws InterruptedException {
        robot.clickOn("#nome").write("usama");
        robot.clickOn("#cognome").write("lb");
        robot.clickOn("#password").write("password");
        robot.clickOn("#username").write("Usama ");
        robot.clickOn("#dataNascita").write("24/06/1998");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        FxAssert.verifyThat("#avantiButton", isEnabled());
        robot.clickOn("#avantiButton").clickOn();
    }
   @Test
    void testButtonBackClick(FxRobot robot) throws InterruptedException {
        robot.clickOn("#indietroButton").clickOn();
    }


    @Test
    void testButtonSocioPlusClick(FxRobot robot) throws InterruptedException {
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
    void testButtonSocioClick(FxRobot robot) throws InterruptedException {
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

