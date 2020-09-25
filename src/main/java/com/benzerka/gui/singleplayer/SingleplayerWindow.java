package com.benzerka.gui.singleplayer;

import com.benzerka.gui.components.PlayableWindow;
import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.gui.components.tile.Tile;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
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
public class SingleplayerWindow extends GridPane implements PlayableWindow {
    @FXML
    private GridPane gameBoard;

    @FXML
    public Label player;

    @FXML
    public Label errorLabel;

    private GameLogic gameLogic;

    private GridPane mainScreen;

    private VBox mainScreenMenu;

    private PlayerModelGetter playerModelGetter;

    @Override
    public void initializeGame(int boardXSize, int boardYSize, int winningConditionSize) {
        gameLogic = new GameLogic(boardXSize, boardYSize, winningConditionSize);
        player.textProperty().bind(gameLogic.getCurrentPlayerProperty().asString());
        errorLabel.textProperty().bind(gameLogic.getErrorProperty());
        createTiles(gameLogic.getGameBoard(), gameLogic.getBoardXSize(), gameLogic.getBoardYSize());
    }

    public SingleplayerWindow(GridPane mainScreen, VBox mainScreenMenu, PlayerModelGetter playerModelGetter) {
        this.playerModelGetter = playerModelGetter;
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SingleplayerRoot.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        errorLabel.getStyleClass().add("error-label");
    }

    private void createTiles(ObjectProperty<TileState>[][] gameBoard, int boardXSize, int boardYSize) {
        for (int i = 0; i < boardYSize; i++) {
            for (int j = 0; j < boardXSize; j++) {
                Tile tile = new Tile(gameBoard[i][j], gameLogic, playerModelGetter//, false
                        );
                tile.sendPosition(j, i);
                this.gameBoard.add(tile, j, i);
            }
        }
    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        gameLogic.clearGameBoard();
        mainScreen.getChildren().setAll(mainScreenMenu);
    }

    public void playAgain(ActionEvent actionEvent) {
        gameLogic.getErrorProperty().set("");
        gameLogic.clearGameBoard();
    }
}
