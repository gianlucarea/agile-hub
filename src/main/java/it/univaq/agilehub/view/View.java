package it.univaq.agilehub.view;

import it.univaq.agilehub.controller.DataInitializable;
import javafx.scene.Parent;

public class View<T> {
    private Parent view;

    private DataInitializable<T> controller;




    public View(Parent nome, DataInitializable<T> controller) {
        this.view = nome;
        this.controller = controller;
    }

    public Parent getView() {
        return this.view;
    }
    public DataInitializable<T> getController(){
        return this.controller;
    }
    public void setView(Parent nome) {
        this.view = nome;
    }
    public void setController(DataInitializable<T> controller) {
        this.controller = controller;
    }
}
