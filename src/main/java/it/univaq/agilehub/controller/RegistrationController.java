package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.utility.Utility;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegistrationController extends DataInitializable<User> implements Initializable {

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
    private CheckBox socioPlusBox;

    @FXML
    private TextField username;
    @FXML
    ImageView calcio = new ImageView();
    @FXML
    ImageView Tennis = new ImageView();
    @FXML
    Label errorLabel = new Label();

    @FXML
    void PasswordAction(ActionEvent event) {}

    @FXML
    void avantiAction(ActionEvent event) {
        Type type ;
        if(socioBox.isSelected()){
            type = Type.SOCIO;
        } else if (socioPlusBox.isSelected()) {
            type = Type.SOCIO_PLUS;
        }else{
            type = Type.NORMALE;
        }
        User user = null;
        String dateOfBirth = Utility.dateOfBirthConverter(dataNascita.getEditor().getText()) ;
        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfBirthTolocalDate = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if(currentDate.isAfter(dateOfBirthTolocalDate)){
            user = new User(nome.getText(), cognome.getText(), password.getText(),username.getText(), dateOfBirthTolocalDate ,type);
            UserDao userDao = new UserDaoImpl();
            userDao.registration(user);
            ViewDispatcher dispatcher = ViewDispatcher.getInstance();
            try {
                dispatcher.logout();
            } catch (ViewException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            errorLabel.setText("Errore nella data di nascita");


        }


    }

    @FXML
    void nomeActione(ActionEvent event) {}

    @FXML
    void cognomeAction(ActionEvent event) {}

    @FXML
    void dataNascitaAction(ActionEvent event) {}

    @FXML
    void usernameAction(ActionEvent event) {}

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
    void socioAction(ActionEvent event) {
        if( socioBox.isSelected()){
            socioPlusBox.setSelected(false);
        }
    }

    @FXML
    void socioPlusAction(ActionEvent event) {
        if( socioPlusBox.isSelected()){
            socioBox.setSelected(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avantiButton.disableProperty()
                .bind(username.textProperty().isEmpty().or(password.textProperty().isEmpty())
                        .or(dataNascita.getEditor().textProperty().isEmpty())
                        .or(nome.textProperty().isEmpty()).or(cognome.textProperty().isEmpty()));
    }
}
