package com.benzerka.gui.components.alerts;

import com.benzerka.logic.TileState;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertCreator {
    private Alert alert;
    private String winner;

    public AlertCreator() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
    }

    public AlertCreator(String winner) {
        this();
        this.winner = winner;
    }

    private AlertResult customizeAlert(String headerText) {
        alert.setTitle("Tic-Tac-Toe Alert");
        alert.setHeaderText(headerText);

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType mainMenu = new ButtonType("Return to Main Menu", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(playAgain, mainMenu);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == playAgain) {
                return AlertResult.PLAY_AGAIN;
            } else if (result.get() == mainMenu) {
                return AlertResult.RETURN_TO_MAIN_MENU;
            }
        }
        return AlertResult.RETURN_TO_MAIN_MENU;
    }

    public AlertResult createTieAlert() {
        return customizeAlert("The result is a tie!");
    }

    public AlertResult createWinAlert() {
        return customizeAlert(winner + " won. Congratulations!");
    }

    public void createLoseAlert() {
        AlertResult userResponse = customizeAlert("You lost.");
    }
}