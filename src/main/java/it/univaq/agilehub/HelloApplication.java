package it.univaq.agilehub;

import it.univaq.agilehub.dao.DaoFactory;
import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Connection connection = DaoFactory.getConnection();

        UserDao userDao = new UserDaoImpl();
        User user1 =  userDao.getUserById(1);
        User user2 =  userDao.getUserById(2);

        System.out.println(user1.toString());
        System.out.println(user2.toString());


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle(user1.getUsername());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}