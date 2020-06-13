package com.benzerka.gui.components.alerts;

import com.benzerka.logic.GameLogic;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertHandler {
    private Alert alert;
    public AlertHandler() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
    }

    public int createTieAlert() {
        alert.setTitle("Tic-Tac-Toe Alert");
        alert.setHeaderText("The result is a tie!");

        ButtonType playAgain = new ButtonType("Play Again");
        ButtonType mainMenu = new ButtonType("Return to Main Menu", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(playAgain, mainMenu);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == playAgain){
                return 1;
            } else if (result.get() == mainMenu) {
                return 2;
            }
        }
        return 2;
    }
}
