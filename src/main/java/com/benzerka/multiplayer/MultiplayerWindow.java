package com.benzerka.multiplayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MultiplayerWindow extends GridPane {
    private MultiplayerController multiplayerController;

    public MultiplayerWindow(GridPane mainScreen, VBox mainScreenMenu) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiplayerRoot.fxml"));
            loader.setRoot(this);
            multiplayerController = new MultiplayerController(mainScreen, mainScreenMenu);
            loader.setController(multiplayerController);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
