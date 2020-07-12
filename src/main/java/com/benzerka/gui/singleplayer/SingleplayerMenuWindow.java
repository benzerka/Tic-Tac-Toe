package com.benzerka.gui.singleplayer;

import com.benzerka.gui.components.GUIEventHandler;
import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.logic.ValidateCustomSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SingleplayerMenuWindow extends GridPane implements Initializable {
    private GridPane mainScreen;
    private VBox mainScreenMenu;
    private SingleplayerWindow singleplayerWindow;
    private GUIEventHandler guiEventHandler;
    private PlayerModelGetter playerModelGetter;
    private ValidateCustomSettings validateCustomSettings;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private GridPane customSettings;

    @FXML
    private TextField boardXSizeTextField;

    @FXML
    private TextField boardYSizeTextField;

    @FXML
    private TextField winningConditionTextField;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validateCustomSettings = new ValidateCustomSettings(boardXSizeTextField, boardYSizeTextField, winningConditionTextField, errorLabel, choiceBox, false);
        errorLabel.getStyleClass().add("error-label");
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            errorLabel.setVisible(false);
            validateCustomSettings.toggleCustomSettings(customSettings, newValue);
        });
        boardXSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateCustomSettings.processNewValue(boardXSizeTextField, newValue);
        });
        boardYSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateCustomSettings.processNewValue(boardYSizeTextField, newValue);
        });
        winningConditionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateCustomSettings.processNewValue(winningConditionTextField, newValue);
        });
    }

    public SingleplayerMenuWindow(GridPane mainScreen, VBox mainScreenMenu, GUIEventHandler guiEventHandler, PlayerModelGetter playerModelGetter) {
        this.playerModelGetter = playerModelGetter;
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        this.guiEventHandler = guiEventHandler;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingleplayerMenuRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }

    public void startGame(ActionEvent actionEvent) {
        singleplayerWindow = new SingleplayerWindow(mainScreen, mainScreenMenu, playerModelGetter);
        if (validateCustomSettings.startGame(singleplayerWindow, choiceBox)) {
            // start game cancelled
        } else {
            // successfully started the game
            guiEventHandler.setPlayableWindow(singleplayerWindow, false);
            guiEventHandler.addPlayableListener(false);
            mainScreen.getChildren().setAll(singleplayerWindow);
            validateCustomSettings.clearTextFieldValues();
        }
    }
}