package com.benzerka.gui.components;

import com.benzerka.logic.TileState;
import com.benzerka.logic.Turn;
import com.benzerka.logic.server.MultiplayerServer;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerModelGetter {
    private TileState firstPlayer;

    private TileState secondPlayer;

    private TileState firstPlayerMultiplayerHost;

    private TileState secondPlayerMultiplayerClient;
    // ???
    @Getter
    @Setter
    private MultiplayerServer multiplayerServer;

    public PlayerModelGetter() {
    }

    public void setTileStateForFirstPlayer(TileState tileState) {
        firstPlayer = tileState;
    }

    public void setTileStateForSecondPlayer(TileState tileState) {
        secondPlayer = tileState;
    }

    public void setTileStateMultiplayerHost(TileState tileState) {
        firstPlayerMultiplayerHost = tileState;
    }

    public void setTileStateMultiplayerClient(TileState tileState) {
        secondPlayerMultiplayerClient = tileState;
    }

    public TileState getPlayerModel(Turn currentTurn) {
        return (currentTurn == Turn.PLAYER1) ? firstPlayer : secondPlayer;
    }

    public TileState getPlayerModelMultiplayer(Turn currentTurn) {
        return (currentTurn == Turn.PLAYER1) ? firstPlayerMultiplayerHost : secondPlayerMultiplayerClient;
    }
}
