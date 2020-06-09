package com.benzerka.singleplayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class SingleplayerWindow extends GridPane {
    public SingleplayerWindow() {
        try {
            FXMLLoader temperatureRoot = new FXMLLoader(getClass().getResource("SingleplayerRoot.fxml"));
            temperatureRoot.setRoot(this);
            temperatureRoot.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
