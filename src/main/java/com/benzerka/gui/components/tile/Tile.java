package com.benzerka.gui.components.tile;

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

    public Pane getEmptyPane() {
        return emptyPane;
    }
    public Pane getCrossPane() {
        return crossPane;
    }
    public Pane getCirclePane() {
        return circlePane;
    }


    public Tile() {
        super();

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
