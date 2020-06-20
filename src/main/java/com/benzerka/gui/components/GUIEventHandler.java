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
    private PlayerModelGetter playerModelGetter;

    public GUIEventHandler(GridPane mainScreen, VBox mainScreenMenu) {
        playerModelGetter = new PlayerModelGetter();
        multiplayerWindow = new MultiplayerWindow(mainScreen, mainScreenMenu);
        optionsWindow = new OptionsWindow(mainScreen, mainScreenMenu, playerModelGetter);
        addMultiplayerListeners();
        singleplayerMenuWindow = new SingleplayerMenuWindow(mainScreen, mainScreenMenu, this, playerModelGetter);
        multiplayerMenuWindow = new MultiplayerMenuWindow(mainScreen, mainScreenMenu, multiplayerWindow);
    }

    public void updateSingleplayerWindow(SingleplayerWindow singleplayerWindow) {
        this.singleplayerWindow = singleplayerWindow;
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
            GUIEventHandler.this.handleSingleplayerTie(alertResult);
            singleplayerWindow.getGameLogic().getErrorProperty().set("");
        });
        // in case of a win
        singleplayerWindow.getGameLogic().addWinListener((startX, endX, startY, endY, type) -> {
            LineCreator lineCreator = new LineCreator();
            lineCreator.sendGameBoard(singleplayerWindow.getGameBoard());
            lineCreator.sendTileXPosition(startX, endX);
            lineCreator.sendTileYPosition(startY, endY);
            lineCreator.setType(type);
            lineCreator.drawWinLine();
            String winner = singleplayerWindow.getGameLogic().getWinner();
            AlertResult alertResult = new AlertCreator(winner).createWinAlert();
            GUIEventHandler.this.handleSingleplayerWin(alertResult);
            singleplayerWindow.getGameLogic().getErrorProperty().set("");
            lineCreator.clearLines();
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
