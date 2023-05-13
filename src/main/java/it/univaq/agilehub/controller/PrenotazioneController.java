package it.univaq.agilehub.controller;

import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PrenotazioneController extends DataInitializable<User> implements Initializable {

    @FXML
    private ChoiceBox<String> campo;

    @FXML
    private DatePicker data;

    @FXML
    private TextField numeroPartecipanti;

    @FXML
    private Button prenota;

    @FXML
    void dataAction(ActionEvent event) {

    }

    @FXML
    void numeroPartecipantiAction(ActionEvent event) {

    }

    @FXML
    void prenotaAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Sport sport : Sport.values()) {
            campo.getItems().add(sport.name());

        }
    }
}
