module it.univaq.agilehub {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.univaq.agilehub to javafx.fxml;
    exports it.univaq.agilehub;
}