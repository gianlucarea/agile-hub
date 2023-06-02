package it.univaq.agilehub.controller;

import it.univaq.agilehub.view.ViewDispatcher;
import it.univaq.agilehub.view.ViewException;
import org.junit.jupiter.api.Test;

public class javaFxTest {
    @Test
    public void testMyMethod()
    {

        ViewDispatcher dispatcher = ViewDispatcher.getInstance();
        try {
            dispatcher.iscrivitiView();
        } catch (ViewException e) {
            throw new RuntimeException(e);
        }
    }
}
