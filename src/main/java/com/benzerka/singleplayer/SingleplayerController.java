package com.benzerka.singleplayer;

import com.benzerka.gui.components.tile.Tile;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
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
        gameLogic = GameLogic.getInstance();
        player.textProperty().bind(gameLogic.getCurrentPlayerProperty().asString());
        errorLabel.textProperty().bind(gameLogic.getErrorProperty());
        createTiles(gameLogic.getGameBoard(), gameLogic.getBoardSize());
    }

    private void createTiles(TileState[][] gameBoard, int boardSize) {
        for(int i = 0; i<boardSize; i++) {
            for(int j = 0; j<boardSize; j++) {
                this.gameBoard.add(new Tile(gameBoard[i][j], i, j), j, i);
            }
        }
    }
}
