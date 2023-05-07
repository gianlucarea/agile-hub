package it.univaq.agilehub.controller;

import it.univaq.agilehub.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController extends DataInitializable<User> implements Initializable {
    User user;

    @FXML
    Label Lbenvenuto = new Label();

    @FXML
    ImageView image = new ImageView();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void initializeData(User user) {
        this.user = user;

        Lbenvenuto.setText("Benvenuto " + user.getName() + "!");

    }
}
