package com.benzerka.singleplayer;

import com.benzerka.gui.components.tile.Tile;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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

    private GridPane mainScreen;
    private VBox mainScreenMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // winning condition musi byc rowny najwiekszemu bokowi, potem mniejszy bok >=2, oba max 20x20
        gameLogic = GameLogic.getInstance(3, 3, 3);
        player.textProperty().bind(gameLogic.getCurrentPlayerProperty().asString());
        errorLabel.textProperty().bind(gameLogic.getErrorProperty());
        createTiles(gameLogic.getGameBoard(), gameLogic.getBoardXSize(), gameLogic.getBoardYSize());
    }

    public SingleplayerController(GridPane mainScreen, VBox mainScreenMenu) {
        this.mainScreen = mainScreen;
        this.mainScreenMenu = mainScreenMenu;
    }

    private void createTiles(ObjectProperty<TileState>[][] gameBoard, int boardXSize, int boardYSize) {
        for (int i = 0; i < boardYSize; i++) {
            for (int j = 0; j < boardXSize; j++) {
                Tile tile = new Tile(gameBoard[i][j]);
                // TODO: alignmenty do ogarnięcia
                tile.sendPosition(j, i);
                this.gameBoard.add(tile, j, i);
            }
        }
    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        mainScreen.getChildren().setAll(mainScreenMenu);
    }

    public void playAgain(ActionEvent actionEvent) {
        gameLogic.clearGameBoard();
    }
}
