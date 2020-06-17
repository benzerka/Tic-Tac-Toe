package com.benzerka.gui.components.tile;

import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class TileController {

    @FXML
    private Line line1;

    @FXML
    private Line line2;

    @FXML
    private Ellipse ellipse;

    @FXML
    private Pane emptyPane;

    @FXML
    private Pane crossPane;

    @FXML
    private Pane ellipsePane;

    private Tile currentTile;

    private GameLogic gameLogic;

    public TileController() {}

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

    public Pane getEllipsePane() {
        return ellipsePane;
    }

    public void setEllipsePane(Pane ellipsePane) {
        this.ellipsePane = ellipsePane;
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

    public Ellipse getEllipse() {
        return ellipse;
    }

    public void setEllipse(Ellipse ellipse) {
        this.ellipse = ellipse;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public void bindShapes() {
        bindEllipse();
        bindCross();
    }

    private void bindCross() {
        line2.startXProperty().setValue(2);
        line2.startYProperty().setValue(2);
        line2.endXProperty().bind(crossPane.widthProperty().subtract(2));
        line2.endYProperty().bind(crossPane.heightProperty().subtract(2));

        line1.startXProperty().setValue(2);
        line1.startYProperty().bind(crossPane.heightProperty().subtract(2));
        line1.endXProperty().bind(crossPane.widthProperty().subtract(2));
        line1.endYProperty().setValue(2);
    }

    private void bindEllipse() {
        ellipse.radiusXProperty().bind(ellipsePane.widthProperty().divide(2));
        ellipse.radiusYProperty().bind(ellipsePane.heightProperty().divide(2));
        ellipse.centerXProperty().bind(ellipsePane.widthProperty().divide(2));
        ellipse.centerYProperty().bind(ellipsePane.heightProperty().divide(2));
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
