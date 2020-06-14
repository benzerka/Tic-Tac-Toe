package com.benzerka.gui.components.tile;

import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class TileController {

    @FXML
    private Line line1;

    @FXML
    private Line line2;

    @FXML
    private Circle circle;

    @FXML
    private Pane emptyPane;

    @FXML
    private Pane crossPane;

    @FXML
    private Pane circlePane;

    private Tile currentTile;

    private GameLogic gameLogic;

    public TileController(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public Pane getEmptyPane() {
        return emptyPane;
    }

    public void setEmptyPane(Pane emptyPane) {
        this.emptyPane = emptyPane;
    }

    public Pane getCrossPane() {
        return crossPane;
    }

    public void setCrossPane(Pane crossPane) {
        this.crossPane = crossPane;
    }

    public Pane getCirclePane() {
        return circlePane;
    }

    public void setCirclePane(Pane circlePane) {
        this.circlePane = circlePane;
    }

    public Line getLine1() {
        return line1;
    }

    public void setLine1(Line line1) {
        this.line1 = line1;
    }

    public Line getLine2() {
        return line2;
    }

    public void setLine2(Line line2) {
        this.line2 = line2;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public void bindShapes() {
//        circlePane.scaleXProperty().addListener((observable, oldValue, newValue) -> {
//            circle.setScaleX(newValue.doubleValue());
//        });
//        circlePane.scaleYProperty().addListener((observable, oldValue, newValue) -> {
//            circle.setScaleY(newValue.doubleValue());
//        });
//        circlePane.widthProperty().addListener((observable, oldValue, newValue) -> {
//            circle.setRadius(newValue.doubleValue());
//        });
        circlePane.heightProperty().addListener((observable, oldValue, newValue) -> {
            circle.setRadius(newValue.doubleValue()/2);
        });
    }

    public void onClick(MouseEvent mouseEvent) {
        applyChanges(gameLogic.getCurrentPlayer());
    }

    private void applyChanges(TileState currentTileState) {
        if (currentTile.getTileState() == TileState.EMPTY) {
            gameLogic.clearError();
            currentTile.setTileState(currentTileState);
            gameLogic.switchTurn();
        } else {
            gameLogic.setError("This tile is occupied.");
        }
    }
}
