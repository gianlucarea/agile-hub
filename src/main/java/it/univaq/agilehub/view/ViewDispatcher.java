package it.univaq.agilehub.view;


import it.univaq.agilehub.controller.DataInitializable;
import it.univaq.agilehub.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewDispatcher {
    private static final String FXML_SUFFIX = ".fxml";
    private static final String RESOURCE_BASE = "/viste/";
    private static ViewDispatcher instance = new ViewDispatcher();
    private Stage stage;
    private BorderPane layout;

    public static ViewDispatcher getInstance() {
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public void loginView(Stage stage) throws ViewException {
        this.stage = stage;
        AnchorPane loginView = (AnchorPane) loadView("login").getView();
        Scene scene = new Scene(loginView);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("AgileHub");
        stage.show();
    }
    public void logout() throws ViewException {
        AnchorPane recoveryView = (AnchorPane) loadView("login").getView();
        Scene scene = new Scene(recoveryView);
        stage.setScene(scene);
    }



    public  void iscrivitiView() throws ViewException{
        AnchorPane iscrivitiView = (AnchorPane) loadView("registration").getView();
        Scene scene = new Scene(iscrivitiView);
        stage.setScene(scene);
    }
    public void homeView(User user) throws ViewException {
        View<User> view = loadView("home");
        DataInitializable<User> homeController = view.getController();
        homeController.initializeData(user);
        layout = (BorderPane) view.getView();


        stage.setX(200.0);
        Scene scene = new Scene(layout);
        stage.setScene(scene);

    }

    public <T> View<T> loadView(String viewName) throws ViewException {
        System.out.println(getClass());
        System.out.println(RESOURCE_BASE + viewName + FXML_SUFFIX);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE_BASE + viewName + FXML_SUFFIX));
            return new View<>(loader.load(),loader.getController());
        } catch (Exception ex) {
            throw new ViewException(ex);
        }

    }

    public <T> void renderView(String nome, T data) {
        try {
            View<T> view = loadView(nome);
            DataInitializable<T> controller = view.getController();

            controller.initializeData(data);
            layout.setCenter(view.getView());
        }
        catch(ViewException e) {
            e.printStackTrace();
        }
    }

}
