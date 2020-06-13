package com.benzerka.main;

import com.benzerka.gui.components.GUIEventHandler;
import com.benzerka.gui.multiplayer.MultiplayerWindow;
import com.benzerka.gui.options.OptionsWindow;
import com.benzerka.gui.singleplayer.SingleplayerWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public GridPane mainScreen;

    @FXML
    public VBox mainScreenMenu;

    @FXML
    private GUIEventHandler guiEventHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        guiEventHandler = new GUIEventHandler(mainScreen, mainScreenMenu);
    }

    public void singleplayer(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(guiEventHandler.getSingleplayerWindow());
    }

    public void multiplayer(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(guiEventHandler.getMultiplayerWindow());
    }

    public void options(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(guiEventHandler.getOptionsWindow());
    }
}
