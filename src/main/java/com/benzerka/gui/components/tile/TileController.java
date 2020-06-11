package com.benzerka.gui.components.tile;

import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class TileController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gameLogic = GameLogic.getInstance();
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
