package it.univaq.agilehub.controller;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends DataInitializable<User> implements Initializable {
    ViewDispatcher dispatcher = ViewDispatcher.getInstance();

    @FXML
    Label errorLabel = new Label();

    @FXML
    TextField username = new TextField();

    @FXML
    PasswordField password = new PasswordField();

    @FXML
    Button accediButton = new Button();

    @FXML
    Button iscrivitiButton = new Button();


    @FXML
    ImageView logo = new ImageView();




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        accediButton.disableProperty().bind(username.textProperty().isEmpty().or(password.textProperty().isEmpty()));
    }
    public void accediAction(){}
    public void iscrivitiAction(){}
}
