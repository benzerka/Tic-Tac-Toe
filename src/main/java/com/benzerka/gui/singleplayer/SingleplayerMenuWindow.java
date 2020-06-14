package com.benzerka.gui.singleplayer;

import com.benzerka.gui.components.GUIEventHandler;
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

    private int boardXSize;
    private int boardYSize;
    private int winningCondition;

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
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            errorLabel.setVisible(false);
            getGameSizeFromUser(newValue);
        });
        boardXSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            scanText(newValue, boardXSizeTextField);
        });
        boardYSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            scanText(newValue,  boardYSizeTextField);
        });
        winningConditionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            scanText(newValue, winningConditionTextField);
        });
    }

    private void scanText(String newValue, TextField textField) {
        errorLabel.setVisible(false);
        if (!newValue.matches("\\d*")) {
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
        String value = textField.getText();
        if (!value.equals("")) {
            int x = Integer.valueOf(value);
            if (x < 2 || x > 20) {
                errorLabel.setVisible(true);
            }
        }
    }

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
        singleplayerWindow = new SingleplayerWindow(mainScreen, mainScreenMenu);
        guiEventHandler.updateSingleplayerWindow(singleplayerWindow);
        singleplayerWindow.initializeGame(boardXSize, boardYSize, winningCondition);
        guiEventHandler.addSingleplayerListeners();
        mainScreen.getChildren().setAll(singleplayerWindow);
    }

    private void getGameSizeFromUser(String newValue) {
        switch (newValue) {
            case "Tic-Tac-Toe":
                customSettings.setDisable(true);
                setGameBoardSize(3, 3, 3);
                break;
            case "Gomoku":
                customSettings.setDisable(true);
                setGameBoardSize(15, 15, 5);
                break;
            case "Custom":
                customSettings.setDisable(false);
                if (!boardXSizeTextField.getText().equals("") || !boardYSizeTextField.getText().equals("") || !winningConditionTextField.getText().equals("")) {
                    setGameBoardSize(Integer.valueOf(boardXSizeTextField.getText()), Integer.valueOf(boardYSizeTextField.getText()), Integer.valueOf(winningConditionTextField.getText()));
                }
                break;
        }
    }

    private void setGameBoardSize(int boardXSize, int boardYSize, int winningCondition) {
        this.boardXSize = boardXSize;
        this.boardYSize = boardYSize;
        this.winningCondition = winningCondition;
    }

    public void goBack(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);

    }
}
