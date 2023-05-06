package it.univaq.agilehub.controller;

import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController extends DataInitializable<User> implements Initializable {

    ViewDispatcher dispatcher = ViewDispatcher.getInstance();

    @FXML
    private Button avantiButton;

    @FXML
    private TextField cognome;

    @FXML
    private DatePicker dataNascita;

    @FXML
    private Button indietroButton;

    @FXML
    private TextField nome;

    @FXML
    private TextField password;

    @FXML
    private CheckBox socioBox;

    @FXML
    private TextField username;

    @FXML
    void PasswordAction(ActionEvent event) {

    }

    @FXML
    void avantiAction(ActionEvent event) {

    }

    @FXML
    void cognomeAction(ActionEvent event) {

    }

    @FXML
    void dataNascitaAction(ActionEvent event) {

    }

    @FXML
    void indietroAction(ActionEvent event) {

    }

    @FXML
    void nomeActione(ActionEvent event) {

    }

    @FXML
    void socioAction(ActionEvent event) {

    }

    @FXML
    void usernameAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
