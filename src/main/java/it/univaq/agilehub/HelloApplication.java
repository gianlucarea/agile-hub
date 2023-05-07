package it.univaq.agilehub;

import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.DaoFactory;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException, ViewException {
        Connection connection = DaoFactory.getConnection();

        UserDao userDao = new UserDaoImpl();
        ViewDispatcher dispatcher = ViewDispatcher.getInstance();
        dispatcher.loginView(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}