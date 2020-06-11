package com.benzerka.singleplayer;

import com.benzerka.gui.components.tile.Tile;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleplayerController implements Initializable {
    @FXML
    private GridPane gameBoard;

    @FXML
    public Label player;

    @FXML
    public Label errorLabel;

    private GameLogic gameLogic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameLogic = GameLogic.getInstance(5, 5, 3);
        player.textProperty().bind(gameLogic.getCurrentPlayerProperty().asString());
        errorLabel.textProperty().bind(gameLogic.getErrorProperty());
        createTiles(gameLogic.getGameBoard(), gameLogic.getBoardXSize(), gameLogic.getBoardYSize());
    }

    private void createTiles(ObjectProperty<TileState>[][] gameBoard, int boardXSize, int boardYSize) {
        for (int i = 0; i < boardYSize; i++) {
            for (int j = 0; j < boardXSize; j++) {
                Tile tile = new Tile(gameBoard[i][j]);
                tile.sendPosition(j, i);
                this.gameBoard.add(tile, j, i);
            }
        }
    }
}
