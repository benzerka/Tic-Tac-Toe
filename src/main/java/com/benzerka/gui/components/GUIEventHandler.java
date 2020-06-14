package com.benzerka.gui.components;

import com.benzerka.gui.components.alerts.AlertCreator;
import com.benzerka.gui.components.alerts.AlertResult;
import com.benzerka.gui.multiplayer.MultiplayerMenuWindow;
import com.benzerka.gui.multiplayer.MultiplayerWindow;
import com.benzerka.gui.options.OptionsWindow;
import com.benzerka.gui.singleplayer.SingleplayerMenuWindow;
import com.benzerka.gui.singleplayer.SingleplayerWindow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GUIEventHandler {
    private SingleplayerWindow singleplayerWindow;
    private SingleplayerMenuWindow singleplayerMenuWindow;
    private MultiplayerWindow multiplayerWindow;

    private MultiplayerMenuWindow multiplayerMenuWindow;

    private OptionsWindow optionsWindow;
    public GUIEventHandler(GridPane mainScreen, VBox mainScreenMenu) {
        singleplayerWindow = new SingleplayerWindow(mainScreen, mainScreenMenu);
        multiplayerWindow = new MultiplayerWindow(mainScreen, mainScreenMenu);
        optionsWindow = new OptionsWindow(mainScreen, mainScreenMenu);
        addMultiplayerListeners();
        singleplayerMenuWindow = new SingleplayerMenuWindow(mainScreen, mainScreenMenu, singleplayerWindow, this);
        multiplayerMenuWindow = new MultiplayerMenuWindow(mainScreen, mainScreenMenu, multiplayerWindow);
    }

    private void addMultiplayerListeners() {
        // MULTIPLAYER
        // in case of a tie

        // in case of a win

        // in case of a lose

    }

    public void addSingleplayerListeners() {
        // SINGLEPLAYER
        // in case of a tie
        singleplayerWindow.getGameLogic().addTieListener(() -> {
            AlertResult alertResult = new AlertCreator().createTieAlert();
            handleSingleplayerTie(alertResult);
            singleplayerWindow.getGameLogic().getErrorProperty().set("");
        });
        // in case of a win
        singleplayerWindow.getGameLogic().addWinListener(() -> {
            AlertResult alertResult = new AlertCreator().createWinAlert();
            handleSingleplayerWin(alertResult);
            singleplayerWindow.getGameLogic().getErrorProperty().set("");
        });
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
        singleplayerWindow.getGameLogic().clearGameBoard();
        singleplayerWindow.getGameLogic().switchTurn();
    }

    public SingleplayerWindow getSingleplayerWindow() {
        return singleplayerWindow;
    }

    public SingleplayerMenuWindow getSingleplayerMenuWindow() {
        return singleplayerMenuWindow;
    }

    public MultiplayerWindow getMultiplayerWindow() {
        return multiplayerWindow;
    }

    public MultiplayerMenuWindow getMultiplayerMenuWindow() {
        return multiplayerMenuWindow;
    }

    public OptionsWindow getOptionsWindow() {
        return optionsWindow;
    }

}
