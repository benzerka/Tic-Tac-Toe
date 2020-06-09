package com.benzerka.main;

import com.benzerka.singleplayer.SingleplayerWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public GridPane mainScreen;

    private SingleplayerWindow singleplayerWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleplayerWindow = new SingleplayerWindow();
    }

    public void play(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(singleplayerWindow);
    }
}
