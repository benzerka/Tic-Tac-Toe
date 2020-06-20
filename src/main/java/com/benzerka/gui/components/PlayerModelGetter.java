package com.benzerka.gui.components;

import com.benzerka.logic.TileState;
import com.benzerka.logic.Turn;


public class PlayerModelGetter {
    private TileState firstPlayer;

    private TileState secondPlayer;

    private TileState multiplayer;

    public PlayerModelGetter() {
    }

    public void setTileStateForFirstPlayer(TileState tileState) {
        firstPlayer = tileState;
    }

    public TileState getFirstPlayer() {
        return firstPlayer;
    }

    public void setTileStateForSecondPlayer(TileState tileState) {
        secondPlayer = tileState;
    }

    public void setTileStateMultiplayer(TileState tileState) {
        secondPlayer = tileState;
    }

    public TileState getSecondPlayer() {
        return secondPlayer;
    }

    public TileState getPlayerModel(Turn currentTurn) {
        return (currentTurn == Turn.PLAYER1) ? firstPlayer : secondPlayer;
    }
}
