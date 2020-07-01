package com.benzerka.gui.components;

import com.benzerka.gui.components.alerts.AlertCreator;
import com.benzerka.gui.components.alerts.AlertResult;
import com.benzerka.gui.multiplayer.MultiplayerMenuWindow;
import com.benzerka.gui.multiplayer.MultiplayerWindow;
import com.benzerka.gui.options.OptionsWindow;
import com.benzerka.gui.singleplayer.SingleplayerMenuWindow;
import com.benzerka.gui.singleplayer.SingleplayerWindow;
import com.benzerka.logic.WinConditionType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

@Getter
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
        singleplayerMenuWindow = new SingleplayerMenuWindow(mainScreen, mainScreenMenu, this, playerModelGetter);
        multiplayerMenuWindow = new MultiplayerMenuWindow(mainScreen, mainScreenMenu, multiplayerWindow);
    }

    public void getSingleplayerWindow(SingleplayerWindow singleplayerWindow) {
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
            handleTie(singleplayerWindow);
        });
        // in case of a win
        singleplayerWindow.getGameLogic().addWinListener((startX, endX, startY, endY, type) -> {
            handleWin(singleplayerWindow, startX, endX, startY, endY, type);
        });
    }

    private void handleWin(PlayableWindow window, int startX, int endX, int startY, int endY, WinConditionType type) {
        LineCreator lineCreator = new LineCreator(window.getGameBoard());
        lineCreator.sendTileXPosition(startX, endX);
        lineCreator.sendTileYPosition(startY, endY);
        lineCreator.setType(type);
        lineCreator.drawWinLine();
        String winner = window.getGameLogic().getWinner();
        AlertResult alertResult = new AlertCreator(winner).createWinAlert();
        GUIEventHandler.this.handleFinishedMatch(window, alertResult);
        window.getGameLogic().getErrorProperty().set("");
        lineCreator.clearLines();
    }

    private void handleTie(PlayableWindow window) {
        AlertResult alertResult = new AlertCreator().createTieAlert();
        GUIEventHandler.this.handleFinishedMatch(window, alertResult);
        window.getGameLogic().getErrorProperty().set("");
    }

    private void handleFinishedMatch(PlayableWindow window, AlertResult alertResult) {
        switch (alertResult) {
            case PLAY_AGAIN:
                clearGameBoardAndSwitchTurn(window);
                break;
            case RETURN_TO_MAIN_MENU:
                clearGameBoardAndSwitchTurn(window);
                window.getMainScreen().getChildren().setAll(window.getMainScreenMenu());
                break;
        }
    }

    private void clearGameBoardAndSwitchTurn(PlayableWindow window) {
        window.getGameLogic().clearGameBoard();
        window.getGameLogic().switchTurn();
    }
}
