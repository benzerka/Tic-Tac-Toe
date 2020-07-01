package com.benzerka.gui.singleplayer;

import com.benzerka.gui.components.GUIEventHandler;
import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.gui.components.alerts.AlertCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        errorLabel.getStyleClass().add("error-label");
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            errorLabel.setVisible(false);
            toggleCustomSettings(newValue);
        });
        boardXSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            processNewValue(boardXSizeTextField, newValue);
        });
        boardYSizeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            processNewValue(boardYSizeTextField, newValue);
        });
        winningConditionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            processNewValue(winningConditionTextField, newValue);
        });
    }

    private void processNewValue(TextField textField, String newValue) {
        replaceLettersToNumbers(newValue, textField);
        errorLabel.setVisible(false);
        String value = textField.getText();
        if (!value.equals("")) {
            int valueAsInt = Integer.valueOf(value);
            // check if the value is out of bounds
            if (valueAsInt < 3 || valueAsInt > 20) {
                if (!errorLabel.getText().equals(incorrectValueText())) {
                    setErrorLabelText(incorrectValueText());
                }
                errorLabel.setVisible(true);
            }
        }
        // check if winning condition number is larger than longer side of game board
        checkLongerSide();
    }

    private void replaceLettersToNumbers(String newValue, TextField textField) {
        // Check if user wrote any letter instead of a number:
        if (!newValue.matches("\\d*")) {
            // change that letter into empty space
            textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }

    private void checkLongerSide() {
        // check the longest side of gameBoard, the winning condition cannot be larger this value.

        // get the longer side of game board
        int longerSide = getLongerSide();

        // if winning condition is larger than longer side
        if (getWinningConditionValue() > longerSide) {
            setErrorLabelText("Win condition cannot be larger than: " + longerSide);
            errorLabel.setVisible(true);
        }
    }

    private int getWinningConditionValue() {
        return getValueIfNotEmptyElseGetZero(winningConditionTextField);
    }

    private int getXValue() {
        return getValueIfNotEmptyElseGetZero(boardXSizeTextField);
    }

    private int getYValue() {
        return getValueIfNotEmptyElseGetZero(boardYSizeTextField);
    }

    private int getValueIfNotEmptyElseGetZero(TextField textField) {
        if (textField.getText().equals("")) {
            return 0;
        } else {
            return Integer.valueOf(textField.getText());
        }
    }

    private void setErrorLabelText(String text) {
        errorLabel.setText(text);
    }

    private String incorrectValueText() {
        return "Please input the value in correct range.";
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
            modifyChoiceBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyChoiceBox() {
        choiceBox.getItems().add(0, "Tic-Tac-Toe");
        choiceBox.getItems().add(1, "Gomoku");
        choiceBox.getItems().add(2, "Custom");
        choiceBox.getSelectionModel().select(0);
    }

    public void startGame(ActionEvent actionEvent) {
        singleplayerWindow = new SingleplayerWindow(mainScreen, mainScreenMenu, playerModelGetter);
        switch (choiceBox.getValue()) {
            case "Tic-Tac-Toe":
                singleplayerWindow.initializeGame(3, 3, 3);
                break;
            case "Gomoku":
                singleplayerWindow.initializeGame(15, 15, 5);
                break;
            case "Custom":
                if (areValuesCorrect()) {
                    singleplayerWindow.initializeGame(getXValue(), getYValue(), getWinningConditionValue());
                } else {
                    AlertCreator alertCreator = new AlertCreator(Alert.AlertType.WARNING);
                    alertCreator.createWarningAlert("Invalid value provided.", "Values must be in range of 3 to 20. Winning condition cannot exceed the longer side.");
                    return;
                }
                break;
        }
        guiEventHandler.getSingleplayerWindow(singleplayerWindow);
        guiEventHandler.addSingleplayerListeners();
        mainScreen.getChildren().setAll(singleplayerWindow);
        clearTextFieldValues();
    }

    private boolean areValuesCorrect() {
        return (checkIfFieldMatchesRange(getXValue()) && checkIfFieldMatchesRange(getYValue()) && checkIfFieldMatchesRange(getWinningConditionValue()) && !checkIfWinningConditionIsLongerThanLongerSide());
    }

    private boolean checkIfFieldMatchesRange(int value) {
        return (value >= 3 && value <= 20);
    }

    private boolean checkIfWinningConditionIsLongerThanLongerSide() {
        int longerSide = getLongerSide();
        return getWinningConditionValue() > longerSide;
    }

    private int getLongerSide() {
        return (getXValue() >= getYValue()) ? getXValue() : getYValue();
    }

    private void clearTextFieldValues() {
        boardXSizeTextField.setText("");
        boardYSizeTextField.setText("");
        winningConditionTextField.setText("");
    }

    private void toggleCustomSettings(String newValue) {
        customSettings.setDisable(!newValue.equals("Custom"));
    }

    public void goBack(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }
}