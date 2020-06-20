package com.benzerka.gui.components.tile;

import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import com.benzerka.logic.Turn;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;

public class TileController {

    @FXML
    private Pane emptyPane;

    @FXML
    private Pane crossPane;

    public Line getLine1() {
        return line1;
    }

    public Line getLine2() {
        return line2;
    }

    public Ellipse getEllipse() {
        return ellipse;
    }

    @FXML
    private Line line1;
    @FXML
    private Line line2;

    @FXML
    private Pane ellipsePane;
    @FXML
    private Ellipse ellipse;

    @FXML
    private Pane trianglePane;
    @FXML
    private Polygon triangle;

    @FXML
    private Pane starPane;

    private Tile currentTile;

    private GameLogic gameLogic;

    private PlayerModelGetter playerModelGetter;


    public TileController(GameLogic gameLogic, PlayerModelGetter playerModelGetter) {
        this.gameLogic = gameLogic;
        this.playerModelGetter = playerModelGetter;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public Pane getEmptyPane() {
        return emptyPane;
    }

    public Pane getCrossPane() {
        return crossPane;
    }

    public Pane getEllipsePane() {
        return ellipsePane;
    }

    public Pane getTrianglePane() {
        return trianglePane;
    }

    public Pane getStarPane() {
        return starPane;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public void bindShapes() {
        bindEllipse();
        bindCross();
        bindTriangle();
        bindStar();
    }

    private void bindStar() {

    }

    private void bindTriangle() {
        //DoubleBinding scaleX = Bindings.createDoubleBinding(() -> trianglePane.widthProperty().divide(100).get(), trianglePane.widthProperty());
        //DoubleBinding scaleX = (DoubleBinding) Bindings.divide(trianglePane.widthProperty(), 100);
        DoubleBinding scaleX = trianglePane.widthProperty().divide(100);
        DoubleBinding scaleY = trianglePane.heightProperty().divide(100);
//        DoubleBinding scaleY = (DoubleBinding) Bindings.divide(trianglePane.heightProperty(), 100);
        //DoubleBinding scaleY = Bindings.createDoubleBinding(() -> trianglePane.heightProperty().divide(100).get(), trianglePane.heightProperty());
        //System.out.println(trianglePane.widthProperty().get());
        //SimpleDoubleProperty scaleX = new SimpleDoubleProperty(trianglePane.widthProperty().divide(100).get());
        //SimpleDoubleProperty scaleY = new SimpleDoubleProperty(trianglePane.heightProperty().divide(100).get());
        double biggerScale = (scaleX.get() >= scaleY.get()) ? scaleX.get() : scaleY.get();
        //biggerScale = (biggerScale == 0) ? biggerScale++ : biggerScale;
        triangle.scaleXProperty().bind(getScaleX(scaleX));
        triangle.scaleYProperty().bind(scaleY);
        DoubleBinding trzy = new DoubleBinding() {
            @Override
            protected double computeValue() {
                double biggerValueNonObservable = (trianglePane.getWidth() >= trianglePane.getHeight()) ? trianglePane.getWidth() : trianglePane.getHeight();
                return biggerValueNonObservable;
            }
        };
        triangle.strokeWidthProperty().bind(trzy);
        //triangle.strokeWidthProperty().addListener(observable -> triangle.setStrokeWidth(getMultiplier(biggerScale.get())));
        //triangle.strokeWidthProperty().bind(getMultiplier(biggerScale.get()));
        //triangle.strokeWidthProperty().bind(biggerScale.multiply(getMultiplier(biggerScale.get())));
        //triangle.strokeWidthProperty().bind(scaleX.multiply(getMultiplier(scaleX.get())));
        //triangle.strokeWidthProperty().setValue(getMultiplier(biggerScale.get()));
        triangle.layoutXProperty().bind(trianglePane.layoutXProperty().add(trianglePane.widthProperty().divide(2).subtract(2)));
        triangle.layoutYProperty().bind(trianglePane.layoutYProperty().add(trianglePane.heightProperty().divide(2).add(8)));
    }

    private DoubleBinding getScaleX(DoubleBinding scaleX) {
        //System.out.println(scaleX);
        return scaleX;
    }

    private double getMultiplier(double biggerScale) {
        return 3 / biggerScale;
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
        Turn currentTurn = gameLogic.getCurrentPlayer();
        TileState playerModel = receivePlayerModel(currentTurn);
        applyChanges(playerModel);
    }

    private TileState receivePlayerModel(Turn currentTurn) {
        return playerModelGetter.getPlayerModel(currentTurn);
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
