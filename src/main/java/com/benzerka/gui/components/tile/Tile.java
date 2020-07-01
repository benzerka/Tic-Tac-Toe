package com.benzerka.gui.components.tile;

import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Tile extends GridPane {
    TileController tileController;

    private ObjectProperty<TileState> tileState;
    private Pane emptyPane;
    private Pane crossPane;
    private Pane ellipsePane;
    private Pane trianglePane;
    private Pane starPane;
    private Pane diamondPane;
    private Pane pentagonPane;
    private int currentXPosition;
    private int currentYPosition;

    public void sendPosition(int x, int y) {
        currentXPosition = x;
        currentYPosition = y;
    }

    public Tile(ObjectProperty<TileState> tileState, GameLogic gameLogic, PlayerModelGetter playerModelGetter) {
        super();
        this.tileState = new SimpleObjectProperty<>();
        this.tileState.bindBidirectional(tileState);
        this.setPadding(new Insets(2, 2, 2, 2));

        this.tileState.addListener((observable, oldValue, newValue) -> {
            if (newValue == TileState.CROSS) {
                crossPane.setVisible(true);
                checkForWinCondition(newValue);
            } else if (newValue == TileState.CIRCLE) {
                ellipsePane.setVisible(true);
                checkForWinCondition(newValue);
            } else if (newValue == TileState.TRIANGLE) {
                trianglePane.setVisible(true);
                checkForWinCondition(newValue);
            } else if (newValue == TileState.STAR) {
                starPane.setVisible(true);
                checkForWinCondition(newValue);
            } else if (newValue == TileState.PENTAGON) {
                pentagonPane.setVisible(true);
                checkForWinCondition(newValue);
            } else if (newValue == TileState.DIAMOND) {
                diamondPane.setVisible(true);
                checkForWinCondition(newValue);
            } else if (newValue == TileState.EMPTY) {
                ellipsePane.setVisible(false);
                crossPane.setVisible(false);
                trianglePane.setVisible(false);
                starPane.setVisible(false);
                diamondPane.setVisible(false);
                pentagonPane.setVisible(false);
                tileController.getGameLogic().resetPlayer();
            }
            emptyPane.setVisible(false);
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TileRoot.fxml"));
            tileController = new TileController(gameLogic, playerModelGetter);
            loader.setController(tileController);
            Node node = loader.load();
            this.getChildren().add(node);
            tileController.setCurrentTile(this);
            emptyPane = tileController.getEmptyPane();
            crossPane = tileController.getCrossPane();
            ellipsePane = tileController.getEllipsePane();
            trianglePane = tileController.getTrianglePane();
            starPane = tileController.getStarPane();
            diamondPane = tileController.getDiamondPane();
            pentagonPane = tileController.getPentagonPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkForWinCondition(TileState newValue) {
        tileController.getGameLogic().checkWinningCondition(newValue, currentXPosition, currentYPosition);
    }


    public TileState getTileState() {
        return tileState.get();
    }

    public void setTileState(TileState tileState) {
        this.tileState.set(tileState);
    }
}
