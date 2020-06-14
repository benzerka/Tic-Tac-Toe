package com.benzerka.gui.components.tile;

import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class Tile extends GridPane {
    TileController tileController;

    private ObjectProperty<TileState> tileState;
    private Pane emptyPane;
    private Pane crossPane;
    private Pane circlePane;
    ObjectProperty<Paint> tileCrossColor = new SimpleObjectProperty<>(Color.RED);
    ObjectProperty<Paint> tileCircleColor = new SimpleObjectProperty<>(Color.BLUE);
    private int currentXPosition;
    private int currentYPosition;

    public void sendPosition(int x, int y) {
        currentXPosition = x;
        currentYPosition = y;
    }

    public Tile(ObjectProperty<TileState> tileState, GameLogic gameLogic) {
        super();
        this.tileState = new SimpleObjectProperty<>();
        this.tileState.bindBidirectional(tileState);
        this.setPadding(new Insets(5, 5, 5, 5));

        this.tileState.addListener((observable, oldValue, newValue) -> {
            if (newValue == TileState.CROSS) {
                crossPane.setVisible(true);
                tileController.getGameLogic().checkWinningCondition(newValue, currentXPosition, currentYPosition);
            } else if (newValue == TileState.CIRCLE) {
                circlePane.setVisible(true);
                tileController.getGameLogic().checkWinningCondition(newValue, currentXPosition, currentYPosition);
            } else if (newValue == TileState.EMPTY) {
                circlePane.setVisible(false);
                crossPane.setVisible(false);
                tileController.getGameLogic().resetPlayer();
            }
            emptyPane.setVisible(false);
        });

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TileRoot.fxml"));
            tileController = new TileController(gameLogic);
            loader.setController(tileController);
            Node node = loader.load();
            this.getChildren().add(node);
            tileController.getLine1().strokeProperty().bind(tileCrossColor);
            tileController.getLine2().strokeProperty().bind(tileCrossColor);
            tileController.getCircle().strokeProperty().bind(tileCircleColor);
            tileController.setCurrentTile(this);
            tileController.bindShapes();
            emptyPane = tileController.getEmptyPane();
            crossPane = tileController.getCrossPane();
            circlePane = tileController.getCirclePane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final Paint getTileCrossColor() {
        return tileCrossColor.get();
    }

    public ObjectProperty<Paint> tileCrossColorProperty() {
        return tileCrossColor;
    }

    public final void setTileCrossColor(Paint tileCrossColor) {
        this.tileCrossColor.set(tileCrossColor);
    }

    public Paint getTileCircleColor() {
        return tileCircleColor.get();
    }

    public ObjectProperty<Paint> tileCircleColorProperty() {
        return tileCircleColor;
    }

    public void setTileCircleColor(Paint tileCircleColor) {
        this.tileCircleColor.set(tileCircleColor);
    }

    public TileState getTileState() {
        return tileState.get();
    }

    public void setTileState(TileState tileState) {
        this.tileState.set(tileState);
    }

    public Pane getEmptyPane() {
        return emptyPane;
    }

    public Pane getCrossPane() {
        return crossPane;
    }

    public Pane getCirclePane() {
        return circlePane;
    }

    public TileController getTileController() {
        return tileController;
    }
}
