package com.benzerka.gui.singleplayer;

import com.benzerka.gui.components.GUIEventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SingleplayerMenuWindow extends GridPane {
    private GridPane mainScreen;
    private VBox mainScreenMenu;
    private SingleplayerWindow singleplayerWindow;
    private GUIEventHandler guiEventHandler;

    private int boardXSize;
    private int boardYSize;
    private int winningCondition;

    @FXML
    private ChoiceBox<String> choiceBox;

    public SingleplayerMenuWindow(GridPane mainScreen, VBox mainScreenMenu, SingleplayerWindow singleplayerWindow, GUIEventHandler guiEventHandler) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        this.singleplayerWindow = singleplayerWindow;
        this.guiEventHandler = guiEventHandler;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingleplayerMenuRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        modifyChoiceBox();
    }

    private void modifyChoiceBox() {
        choiceBox.getItems().add(0, "Tic-Tac-Toe");
        choiceBox.getItems().add(1, "Gomoku");
        choiceBox.getItems().add(2, "Custom");
        choiceBox.getSelectionModel().select(0);
    }

    public void startGame(ActionEvent actionEvent) {
        getGameSizeFromUser();
        singleplayerWindow.initializeGame(boardXSize, boardYSize, winningCondition);
        guiEventHandler.addSingleplayerListeners();
        mainScreen.getChildren().setAll(singleplayerWindow);
    }

    private void getGameSizeFromUser() {
        switch(choiceBox.getValue()) {
            case "Tic-Tac-Toe":
                boardXSize = 3;
                boardYSize = 3;
                winningCondition = 3;
                break;
            case "Gomoku":
                boardXSize = 15;
                boardYSize = 15;
                winningCondition = 5;
                break;
            case "Custom":

                break;
        }
    }

    public void goBack(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);

    }
}
