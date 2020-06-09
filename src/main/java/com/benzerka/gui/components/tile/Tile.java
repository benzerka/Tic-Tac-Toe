package com.benzerka.gui.components.tile;

import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;

public class Tile extends GridPane {
    TileController tileController;
    private int row;
    private int column;

    private TileState tileState;

    ObjectProperty<Paint> tileCrossColor = new SimpleObjectProperty<>(Color.RED);

    ObjectProperty<Paint> tileCircleColor = new SimpleObjectProperty<>(Color.BLUE);
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
    private Pane emptyPane;

    private Pane crossPane;

    private Pane circlePane;
    public TileState getTileState() {
        return tileState;
    }

    public void setTileState(TileState tileState) {
        this.tileState = tileState;
        GameLogic.getInstance().setTile(row, column, tileState);
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


    public Tile(TileState tileState, int row, int column) {
        super();
        this.tileState = tileState;
        this.row = row;
        this.column = column;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TileRoot.fxml"));
            tileController = new TileController();
            loader.setController(tileController);
            Node node = loader.load();
            this.getChildren().add(node);
            tileController.getLine1().strokeProperty().bind(tileCrossColor);
            tileController.getLine2().strokeProperty().bind(tileCrossColor);
            tileController.getCircle().strokeProperty().bind(tileCircleColor);
            tileController.setCurrentTile(this);
            emptyPane = tileController.getEmptyPane();
            crossPane = tileController.getCrossPane();
            circlePane = tileController.getCirclePane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
