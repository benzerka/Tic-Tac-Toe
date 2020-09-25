package com.benzerka.gui.multiplayer;

import com.benzerka.gui.components.PlayableWindow;
import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.gui.components.tile.Tile;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
//import com.benzerka.logic.Turn;
//import javafx.beans.binding.Bindings;
import com.benzerka.logic.server.MultiplayerServer;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;

@Getter
public class MultiplayerWindow extends GridPane implements PlayableWindow {
    private GridPane mainScreen;
    private VBox mainScreenMenu;
    private PlayerModelGetter playerModelGetter;
    private GameLogic gameLogic;

    @FXML
    public Label player;

    @FXML
    public Label errorLabel;

    @FXML
    private GridPane gameBoard;

    //
    private String hostNickname;
    //
    private String clientNickname;

    public MultiplayerWindow(GridPane mainScreen, VBox mainScreenMenu, PlayerModelGetter playerModelGetter) {
        this.playerModelGetter = playerModelGetter;
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiplayerRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        errorLabel.getStyleClass().add("error-label");
    }

    public void setServerToPlayerModelGetter(MultiplayerServer multiplayerServer) {
        playerModelGetter.setMultiplayerServer(multiplayerServer);
    }

    @Override
    public void initializeGame(int boardXSize, int boardYSize, int winningConditionSize) {
        gameLogic = new GameLogic(boardXSize, boardYSize, winningConditionSize);
        //gameLogic.setMultiplayerFlagOn();
        //tu ustawiamy label
        player.textProperty().bind(
                gameLogic.getCurrentPlayerProperty().asString()); //temporary, from old main branch
//                Bindings.createStringBinding(() -> {
//                    if (gameLogic.getCurrentPlayerProperty().isEqualTo(Turn.PLAYER1).get()) {
//                        return hostNickname;
//                    } else {
//                        return clientNickname;
//                    }
//                }, gameLogic.getCurrentPlayerProperty()));
        errorLabel.textProperty().bind(gameLogic.getErrorProperty());
        createTiles(gameLogic.getGameBoard(), gameLogic.getBoardXSize(), gameLogic.getBoardYSize());
    }

    private void createTiles(ObjectProperty<TileState>[][] gameBoard, int boardXSize, int boardYSize) {
        for (int i = 0; i < boardYSize; i++) {
            for (int j = 0; j < boardXSize; j++) {
                Tile tile = new Tile(gameBoard[i][j], gameLogic, playerModelGetter//, true
                        );
                tile.sendPosition(j, i);
                this.gameBoard.add(tile, j, i);
            }
        }
    }

    //
    public void setMultiplayerNickname(String nickname, boolean isHost) {
        if (isHost) {
            hostNickname = nickname;
        } else {
            clientNickname = nickname;
        }
    }
//
//    public void setMultiplayerNicknameInGameLogic() {
//        gameLogic.setMultiplayerHostNickname(hostNickname);
//        gameLogic.setMultiplayerClientNickname(clientNickname);
//    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        gameLogic.clearGameBoard();
        mainScreen.getChildren().setAll(mainScreenMenu);
    }

    public void playAgain(ActionEvent actionEvent) {
        // TODO: możliwe że do usunięcia
        gameLogic.getErrorProperty().set("");
        gameLogic.clearGameBoard();
    }
}
