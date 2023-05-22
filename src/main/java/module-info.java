module it.univaq.agilehub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;


    opens it.univaq.agilehub to javafx.fxml;
    exports it.univaq.agilehub;
    opens it.univaq.agilehub.controller to javafx.fxml;
    exports it.univaq.agilehub.controller;
    opens it.univaq.agilehub.model to javafx.fxml;
    exports it.univaq.agilehub.model;
}