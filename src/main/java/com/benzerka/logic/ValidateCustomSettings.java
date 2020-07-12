package com.benzerka.logic;

import com.benzerka.gui.components.PlayableWindow;
import com.benzerka.gui.components.alerts.AlertCreator;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class ValidateCustomSettings {
    private Label errorLabel;
    private TextField boardXSizeTextField;
    private TextField boardYSizeTextField;
    private TextField winningConditionTextField;
    private boolean isMultiplayer;
    private MultiplayerServerThread serverThread = null;
    private ChoiceBox<String> choiceBox;

    public ValidateCustomSettings(TextField boardXSizeTextField, TextField boardYSizeTextField, TextField winningConditionTextField, Label errorLabel, ChoiceBox<String> choiceBox, boolean isMultiplayer) {
        this.boardXSizeTextField = boardXSizeTextField;
        this.boardYSizeTextField = boardYSizeTextField;
        this.winningConditionTextField = winningConditionTextField;
        this.errorLabel = errorLabel;
        this.isMultiplayer = isMultiplayer;
        this.choiceBox = choiceBox;
        modifyChoiceBox();
    }

    private void modifyChoiceBox() {
        choiceBox.getItems().add(0, "Tic-Tac-Toe");
        choiceBox.getItems().add(1, "Gomoku");
        choiceBox.getItems().add(2, "Custom");
        choiceBox.getSelectionModel().select(0);
    }

    public void getServerThread(MultiplayerServerThread serverThread) {
        this.serverThread = serverThread;
    }

    public void processNewValue(TextField textField, String newValue) {
        //replaceLettersToNumbers(newValue, textField);
        ValidateTextField validateTextField = new ValidateTextField(textField);
        validateTextField.replaceLettersToNumbers(newValue);
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

    private void setErrorLabelText(String text) {
        errorLabel.setText(text);
    }

    private int getLongerSide() {
        return (getXValue() >= getYValue()) ? getXValue() : getYValue();
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

    public void clearTextFieldValues() {
        boardXSizeTextField.setText("");
        boardYSizeTextField.setText("");
        winningConditionTextField.setText("");
    }

    public void toggleCustomSettings(GridPane customSettings, String newValue) {
        customSettings.setDisable(!newValue.equals("Custom"));
    }

    public boolean startGame(PlayableWindow window, ChoiceBox<String> choiceBox) {
        switch (choiceBox.getValue()) {
            case "Tic-Tac-Toe":
                if (isMultiplayer) {
                    if (Objects.nonNull(serverThread)) {
                        serverThread.getMultiplayerServer().sendInstructionsToClient("3,3,3");
                    }
                }
                window.initializeGame(3, 3, 3);
                break;
            case "Gomoku":
                if (isMultiplayer) {
                    if (Objects.nonNull(serverThread)) {
                        serverThread.getMultiplayerServer().sendInstructionsToClient("15,15,5");
                    }
                }
                window.initializeGame(15, 15, 5);
                break;
            case "Custom":
                if (areValuesCorrect()) {
                    if (isMultiplayer) {
                        if (Objects.nonNull(serverThread)) {
                            serverThread.getMultiplayerServer().sendInstructionsToClient(getXValue() + "," + getYValue() + "," + getWinningConditionValue());
                        }
                    }
                    window.initializeGame(getXValue(), getYValue(), getWinningConditionValue());
                } else {
                    AlertCreator alertCreator = new AlertCreator(Alert.AlertType.WARNING);
                    alertCreator.createWarningAlert("Invalid value provided.", "Values must be in range of 3 to 20. Winning condition cannot exceed the longer side.");
                    return true;
                }
                break;
        }
        return false;
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

    private String incorrectValueText() {
        return "Please input the value in correct range.";
    }
}
