package it.univaq.agilehub.controller;

import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.MenuElement;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static it.univaq.agilehub.model.Type.ADMIN;

public class HomeController  extends DataInitializable<User> implements Initializable {
    @FXML
    VBox menuBar  = new VBox();

    @FXML
    ImageView logout = new ImageView();

    @FXML
    Label profile = new Label();
    @FXML
    Label profileType = new Label();

    private ViewDispatcher dispatcher = ViewDispatcher.getInstance();
    private Stage stage = dispatcher.getStage();
    private User user;


    private static final MenuElement[] menuUser = {new MenuElement("Prenota Campo", "prenotazioni"), new MenuElement("Lezioni", "prenotaMaestri")};
    private static final MenuElement[] menuAdmin= {new MenuElement("Maestri", "registraMaestri")};
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initializeData(User user) throws ViewException {
        this.user = user;
        profile.setText(user.getName() + "\n" + user.getSurname());
        profileType.setText(user.getType().toString());
        if(user.getType() == ADMIN){
            menuBar.getChildren().add(createButton("Home","welcome"));
            menuBar.getChildren().add(new Separator());
            for(MenuElement element: menuAdmin) {
                menuBar.getChildren().add(createButton(element.getButtonName(), element.getViewName()));

        }



    }
        else{
            menuBar.getChildren().add(createButton("Home","welcome"));
            menuBar.getChildren().add(new Separator());
            for(MenuElement element: menuUser) {
                menuBar.getChildren().add(createButton(element.getButtonName(), element.getViewName()));

            }
        }

        }
    public void logoutAction() throws ViewException {
        dispatcher.logout();
    }



    private Button createButton(String name, String view) {
        Button button = new Button(name);
        button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;");
        button.setTextFill(Paint.valueOf("White"));
        button.setPrefHeight(35);
        button.setPrefWidth(180);

        button.setOnAction((ActionEvent event) -> {
            dispatcher.renderView(view, user);
        });


        return button;

    }
}