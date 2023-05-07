package it.univaq.agilehub.controller;

import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController  extends DataInitializable<User> implements Initializable {

    @FXML
    ImageView logout = new ImageView();

    @FXML
    Label profile = new Label();
    private ViewDispatcher dispatcher = ViewDispatcher.getInstance();
    private Stage stage = dispatcher.getStage();
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initializeData(User user) {
        this.user = user;


        profile.setText(user.getName() + "\n" + user.getSurname());
    }
    public void logoutAction() throws ViewException {
        dispatcher.logout();
    }

}