package com.benzerka.gui.singleplayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SingleplayerWindow extends GridPane {
    private SingleplayerController singleplayerController;
    private GridPane mainScreen;
    private VBox mainScreenMenu;

    public SingleplayerWindow(GridPane mainScreen, VBox mainScreenMenu) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingleplayerRoot.fxml"));
            loader.setRoot(this);
            singleplayerController = new SingleplayerController(mainScreen, mainScreenMenu);
            loader.setController(singleplayerController);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SingleplayerController getSingleplayerController() {
        return singleplayerController;
    }

    public GridPane getMainScreen() {
        return mainScreen;
    }

    public VBox getMainScreenMenu() {
        return mainScreenMenu;
    }
}
