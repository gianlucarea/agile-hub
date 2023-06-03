package it.univaq.agilehub.controller;

import com.sun.glass.events.KeyEvent;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;


import java.awt.event.InputEvent;

import static it.univaq.agilehub.model.Sport.CALCETTO;
import static it.univaq.agilehub.model.Sport.PALLAVOLO;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingControllerTest {
    @BeforeAll
    public static void setupSpec() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @BeforeEach
    public void start() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/viste/prenotazione.fxml"));
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
    void testButtonClick2(FxRobot robot) throws InterruptedException {
        robot.clickOn("#selezioneTipologia").clickOn(String.valueOf(PALLAVOLO));
        robot.clickOn("#data").write("04/06/2023").press(KeyCode.ENTER);
        robot.clickOn("#numeroPartecipanti").write("12");
        robot.clickOn("#selezioneCampo").clickOn("Pallavolo 2");
        robot.clickOn("#selezioneOrario").clickOn("10:00 - 11:00");







    }
}

