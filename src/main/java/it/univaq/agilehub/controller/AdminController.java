package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.Type;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AdminController extends DataInitializable<User> implements Initializable {

    @FXML
    private Button avantiMaestroButton;

    @FXML
    private TextField cognomeMaestro;

    @FXML
    private DatePicker dataNascitaMaestro;

    @FXML
    private TextField nomeMaesto;

    @FXML
    private TextField passwordMaestro;

    @FXML
    private ChoiceBox<String> sportMaestro;

    @FXML
    private TextField usernameMaestro;

    @FXML
    private Label confermaRegistrazioneMaestro;

    @FXML
    void PasswordMaestroAction(ActionEvent event) {}

    @FXML
    void avantiMaestroAction(ActionEvent event) {
        User user = null;
        String dateOfBirth =  dataNascitaMaestro.getValue().toString();
        LocalDate dateOfBirthTolocalDate = LocalDate.parse(Utility.dateOfBirthConverter(dateOfBirth), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String sport = sportMaestro.getValue();
        user = new User(nomeMaesto.getText(), cognomeMaestro.getText(), passwordMaestro.getText(),usernameMaestro.getText(), dateOfBirthTolocalDate , Type.MAESTRO, Sport.valueOf(sport));
        UserDao userDao = new UserDaoImpl();
        userDao.registrationAdmin(user);

        confermaRegistrazioneMaestro.setText("Registrazione confermata");
        nomeMaesto.setText(null);
        cognomeMaestro.setText(null);
        usernameMaestro.setText(null);
        passwordMaestro.setText(null);
        dataNascitaMaestro.setValue(null);
        sportMaestro.setValue(null);
    }

    @FXML
    void cognomeMaestroAction(ActionEvent event) {}

    @FXML
    void dataNascitaMaestroAction(ActionEvent event) {}

    @FXML
    void nomeMaestroAction(ActionEvent event) {}

    @FXML
    void usernameMaestroAction(ActionEvent event) {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avantiMaestroButton.disableProperty()
                .bind(usernameMaestro.textProperty().isEmpty().or(passwordMaestro.textProperty().isEmpty())
                        .or(dataNascitaMaestro.valueProperty().isNull())
                        .or(nomeMaesto.textProperty().isEmpty()).or(cognomeMaestro.textProperty().isEmpty()));

        for (Sport sport : Sport.values()){
            sportMaestro.getItems().add(sport.name());
        }
    }

}
