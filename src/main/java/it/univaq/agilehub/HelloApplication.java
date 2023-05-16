package it.univaq.agilehub;



import it.univaq.agilehub.dao.TeacherBookingDao;
import it.univaq.agilehub.dao.TeacherBookingDaoImpl;
import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import javafx.application.Application;

import javafx.stage.Stage;

import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override

    public void start(Stage stage) throws ViewException {
        ViewDispatcher dispatcher = ViewDispatcher.getInstance();
        dispatcher.loginView(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}