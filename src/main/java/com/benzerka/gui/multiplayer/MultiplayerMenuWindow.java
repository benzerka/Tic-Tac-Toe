package com.benzerka.gui.multiplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MultiplayerMenuWindow extends GridPane {
    private GridPane mainScreen;
    private VBox mainScreenMenu;
    private MultiplayerWindow multiplayerWindow;

    public MultiplayerMenuWindow(GridPane mainScreen, VBox mainScreenMenu, MultiplayerWindow multiplayerWindow) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        this.multiplayerWindow = multiplayerWindow;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiplayerMenuRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(multiplayerWindow);
    }

    public void goBack(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }
}
