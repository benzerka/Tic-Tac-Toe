package com.benzerka.options;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class OptionsController {
    private GridPane mainScreen;
    private VBox mainScreenMenu;

    public OptionsController(GridPane mainScreen, VBox mainScreenMenu) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }
}
