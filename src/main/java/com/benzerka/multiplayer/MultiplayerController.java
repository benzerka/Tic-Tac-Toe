package com.benzerka.multiplayer;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MultiplayerController {
    private GridPane mainScreen;
    private VBox mainScreenMenu;

    public MultiplayerController(GridPane mainScreen, VBox mainScreenMenu) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }
}
