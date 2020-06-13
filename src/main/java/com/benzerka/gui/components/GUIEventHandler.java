package com.benzerka.gui.components;

import com.benzerka.gui.components.alerts.AlertCreator;
import com.benzerka.gui.components.alerts.AlertResult;
import com.benzerka.gui.multiplayer.MultiplayerController;
import com.benzerka.gui.multiplayer.MultiplayerWindow;
import com.benzerka.gui.options.OptionsController;
import com.benzerka.gui.options.OptionsWindow;
import com.benzerka.gui.singleplayer.SingleplayerController;
import com.benzerka.gui.singleplayer.SingleplayerWindow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GUIEventHandler {
    private SingleplayerWindow singleplayerWindow;
    private SingleplayerController singleplayerController;
    private MultiplayerWindow multiplayerWindow;
    private MultiplayerController multiplayerController;
    private OptionsWindow optionsWindow;
    private OptionsController optionsController;

    public GUIEventHandler(GridPane mainScreen, VBox mainScreenMenu) {
        singleplayerWindow = new SingleplayerWindow(mainScreen, mainScreenMenu);
        singleplayerController = singleplayerWindow.getSingleplayerController();
        multiplayerWindow = new MultiplayerWindow(mainScreen, mainScreenMenu);
        multiplayerController = multiplayerWindow.getMultiplayerController();
        optionsWindow = new OptionsWindow(mainScreen, mainScreenMenu);
        optionsController = optionsWindow.getOptionsController();
        // SINGLEPLAYER
        // in case of a tie
        singleplayerController.getGameLogic().addTieListener(() -> {
            AlertResult alertResult = new AlertCreator().createTieAlert();
            handleSingleplayerTie(alertResult);
            singleplayerController.getGameLogic().getErrorProperty().set("");
        });
        // in case of a win
        singleplayerController.getGameLogic().addWinListener(() -> {
            AlertResult alertResult = new AlertCreator().createWinAlert();
            handleSingleplayerWin(alertResult);
            singleplayerController.getGameLogic().getErrorProperty().set("");
        });
        // MULTIPLAYER
        // in case of a tie

        // in case of a win

        // in case of a lose

    }

    private void handleSingleplayerWin(AlertResult alertResult) {
        handleSingleplayerTie(alertResult);
    }

    private void handleSingleplayerTie(AlertResult alertResult) {
        switch (alertResult) {
            case PLAY_AGAIN:
                clearGameBoardAndSwitchTurn();
                break;
            case RETURN_TO_MAIN_MENU:
                clearGameBoardAndSwitchTurn();
                singleplayerWindow.getMainScreen().getChildren().setAll(singleplayerWindow.getMainScreenMenu());
                break;
        }
    }

    private void clearGameBoardAndSwitchTurn() {
        singleplayerController.getGameLogic().clearGameBoard();
        singleplayerController.getGameLogic().switchTurn();
    }

    public SingleplayerWindow getSingleplayerWindow() {
        return singleplayerWindow;
    }

    public MultiplayerWindow getMultiplayerWindow() {
        return multiplayerWindow;
    }

    public OptionsWindow getOptionsWindow() {
        return optionsWindow;
    }

}
