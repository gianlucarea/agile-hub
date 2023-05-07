package it.univaq.agilehub.controller;

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

public class AdminController extends DataInitializable<User> implements Initializable {

    @FXML
    private Button avantiMaestroButton;

    @FXML
    private TextField cognomeMaestro;

    @FXML
    private DatePicker dataNascitaMaestro;

    @FXML
    private Button indietroMaestriButton;

    @FXML
    private TextField nomeMaesto;

    @FXML
    private TextField passwordMaestro;

    @FXML
    private ChoiceBox<?> sportMaestro;

    @FXML
    private TextField usernameMaestro;

    @FXML
    void PasswordMaestroAction(ActionEvent event) {

    }

    @FXML
    void avantiMaestroAction(ActionEvent event) {

    }

    @FXML
    void cognomeMaestroAction(ActionEvent event) {

    }

    @FXML
    void dataNascitaMaestroAction(ActionEvent event) {

    }

    @FXML
    void indietroMaestriAction(ActionEvent event) {

    }

    @FXML
    void nomeMaestroAction(ActionEvent event) {

    }

    @FXML
    void usernameMaestroAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avantiMaestroButton.disableProperty()
                .bind(usernameMaestro.textProperty().isEmpty().or(passwordMaestro.textProperty().isEmpty())
                        .or(dataNascitaMaestro.valueProperty().isNull())
                        .or(nomeMaesto.textProperty().isEmpty()).or(cognomeMaestro.textProperty().isEmpty()));
    }
}
