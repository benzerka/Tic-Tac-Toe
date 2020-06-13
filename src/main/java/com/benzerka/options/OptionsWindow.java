package com.benzerka.options;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OptionsWindow extends GridPane {
    private OptionsController optionsController;

    public OptionsWindow(GridPane mainScreen, VBox mainScreenMenu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsRoot.fxml"));
            loader.setRoot(this);
            optionsController = new OptionsController(mainScreen, mainScreenMenu);
            loader.setController(optionsController);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
