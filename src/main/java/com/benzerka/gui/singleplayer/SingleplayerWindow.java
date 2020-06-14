package com.benzerka.gui.singleplayer;

import com.benzerka.gui.components.tile.Tile;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import com.sun.org.apache.xml.internal.security.Init;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SingleplayerWindow extends GridPane {
    @FXML
    private GridPane gameBoard;

    @FXML
    public Label player;

    @FXML
    public Label errorLabel;

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    private GameLogic gameLogic;

    private GridPane mainScreen;
    private VBox mainScreenMenu;

    public void initializeGame(int boardXSize, int boardYSize, int winningConditionSize) {
        super.getChildren().removeAll();
        overridePreviousTiles();
        gameLogic = new GameLogic(boardXSize, boardYSize, winningConditionSize);
        player.textProperty().bind(gameLogic.getCurrentPlayerProperty().asString());
        errorLabel.textProperty().bind(gameLogic.getErrorProperty());
        createTiles(gameLogic.getGameBoard(), gameLogic.getBoardXSize(), gameLogic.getBoardYSize());
    }

    public SingleplayerWindow(GridPane mainScreen, VBox mainScreenMenu) {
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
    }

    public GridPane getMainScreen() {
        return mainScreen;
    }

    public VBox getMainScreenMenu() {
        return mainScreenMenu;
    }

    private void createTiles(ObjectProperty<TileState>[][] gameBoard, int boardXSize, int boardYSize) {
        for (int i = 0; i < boardYSize; i++) {
            for (int j = 0; j < boardXSize; j++) {
                Tile tile = new Tile(gameBoard[i][j], gameLogic);
                // TODO: alignmenty do ogarnięcia
                tile.sendPosition(j, i);
                this.gameBoard.add(tile, j, i);
            }
        }
    }

    private void overridePreviousTiles() {
        gameBoard = new GridPane();
        gameBoard.setGridLinesVisible(true);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.CENTER);
        column1.setMinWidth(0.0);
        column1.setPrefWidth(110);
        column1.setMaxWidth(Double.MAX_VALUE);
        RowConstraints row1 = new RowConstraints();
        row1.setValignment(VPos.CENTER);
        row1.setMinHeight(0.0);
        row1.setPrefHeight(110);
        row1.setMaxHeight(Double.MAX_VALUE);
        gameBoard.getColumnConstraints().add(column1);
        gameBoard.getRowConstraints().add(row1);
        super.getChildren().add(gameBoard);
    }

    public void returnToMainScreen(ActionEvent actionEvent) {
        gameLogic.clearGameBoard();
        mainScreen.getChildren().setAll(mainScreenMenu);
    }

    public void playAgain(ActionEvent actionEvent) {
        gameLogic.clearGameBoard();
    }
}
