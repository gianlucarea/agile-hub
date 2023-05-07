package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.DaoFactory;
import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
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
        Type type ;
        if(socioBox.isSelected()){
            type = Type.SOCIO;
        } else {
            type = Type.NORMALE;
        }
        User user = null;
        String dateOfBirth =  User.dateOfBirthConverter(dataNascita.getValue().toString());
        user = new User(nome.getText(), cognome.getText(), password.getText(),username.getText(), dateOfBirth ,type);
        UserDao userDao = new UserDaoImpl();
        userDao.registration(user);
    }

    @FXML
    void cognomeAction(ActionEvent event) {
    }

    @FXML
    void dataNascitaAction(ActionEvent event) {
    }

    @FXML
    void indietroAction(ActionEvent event) {
        ViewDispatcher dispatcher = ViewDispatcher.getInstance();
        try {
            dispatcher.logout();
        } catch (ViewException e) {
            throw new RuntimeException(e);
        }
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
        avantiButton.disableProperty()
                .bind(username.textProperty().isEmpty().or(password.textProperty().isEmpty())
                        .or(dataNascita.valueProperty().isNull())
                        .or(nome.textProperty().isEmpty()).or(cognome.textProperty().isEmpty()));
    }
}
