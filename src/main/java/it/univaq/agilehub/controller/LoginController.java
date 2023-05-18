package it.univaq.agilehub.controller;
import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.event.ActionEvent;
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

    UserDao userDao = new UserDaoImpl();

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

    public void accediAction(ActionEvent event) throws ViewException {
        try {
            User user = userDao.authenticate(username.getText(), password.getText());
            if(user != null){
                dispatcher.homeView(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Username o password errati!");
        }
    }

    public void iscrivitiAction() {
        ViewDispatcher dispatcher = ViewDispatcher.getInstance();
        try {
            dispatcher.iscrivitiView();
        } catch (ViewException e) {
            throw new RuntimeException(e);
        }
    }

}