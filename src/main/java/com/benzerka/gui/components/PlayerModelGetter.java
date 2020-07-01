package com.benzerka.gui.components;

import com.benzerka.logic.TileState;
import com.benzerka.logic.Turn;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerModelGetter {
    private TileState firstPlayer;

    private TileState secondPlayer;

    private TileState multiplayer;

    public PlayerModelGetter() {
    }

    public void setTileStateForFirstPlayer(TileState tileState) {
        firstPlayer = tileState;
    }

    public void setTileStateForSecondPlayer(TileState tileState) {
        secondPlayer = tileState;
    }

    public void setTileStateMultiplayer(TileState tileState) {
        multiplayer = tileState;
    }

    public TileState getPlayerModel(Turn currentTurn) {
        return (currentTurn == Turn.PLAYER1) ? firstPlayer : secondPlayer;
    }
}
