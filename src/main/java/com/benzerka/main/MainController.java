package com.benzerka.main;

import com.benzerka.multiplayer.MultiplayerWindow;
import com.benzerka.options.OptionsWindow;
import com.benzerka.singleplayer.SingleplayerWindow;
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

    private SingleplayerWindow singleplayerWindow;
    private MultiplayerWindow multiplayerWindow;
    private OptionsWindow optionsWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleplayerWindow = new SingleplayerWindow(mainScreen, mainScreenMenu);
        multiplayerWindow = new MultiplayerWindow(mainScreen, mainScreenMenu);
        optionsWindow = new OptionsWindow(mainScreen, mainScreenMenu);
    }

    public void singleplayer(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(singleplayerWindow);
    }

    public void multiplayer(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(multiplayerWindow);
    }

    public void options(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(optionsWindow);
    }
}
