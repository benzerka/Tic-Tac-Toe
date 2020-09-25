package com.benzerka.gui.components.tile;

import com.benzerka.gui.components.PlayerModelGetter;
import com.benzerka.logic.GameLogic;
import com.benzerka.logic.TileState;
import com.benzerka.logic.Turn;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class TileController implements Initializable {

    @FXML
    private Pane emptyPane;

    @FXML
    private Pane crossPane;
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
    @FXML
    private Polygon star;

    @FXML
    private Pane pentagonPane;
    @FXML
    private Polygon pentagon;

    @FXML
    private Pane diamondPane;
    @FXML
    private Polygon diamond;

    private Tile currentTile;
    private GameLogic gameLogic;

    private PlayerModelGetter playerModelGetter;
    //private boolean isMultiplayer;


    public TileController(GameLogic gameLogic, PlayerModelGetter playerModelGetter//, boolean isMultiplayer
                          ) {
        this.gameLogic = gameLogic;
        this.playerModelGetter = playerModelGetter;
        //this.isMultiplayer = isMultiplayer;
    }

    public void bindShapes() {
        bindEllipse();
        bindCross();
        bindTriangle();
        bindStar();
        bindPentagon();
        bindDiamond();
    }

    private void bindPentagon() {
        polygonBinding(pentagon, pentagonPane);
    }

    private void bindDiamond() {
        polygonBinding(diamond, diamondPane);
    }

    private void bindStar() {
        polygonBinding(star, starPane);
    }

    private void bindTriangle() {
        polygonBinding(triangle, trianglePane);
    }

    private void polygonBinding(Polygon polygon, Pane pane) {
        DoubleBinding scaleX = pane.widthProperty().divide(100);
        DoubleBinding scaleY = pane.heightProperty().divide(100);
        polygon.scaleXProperty().bind(scaleX);
        polygon.scaleYProperty().bind(scaleY);
        polygon.strokeWidthProperty().bind(Bindings.divide(3, Bindings.max(scaleX, scaleY)));
        polygon.layoutXProperty().bind(pane.layoutXProperty().add(pane.widthProperty().divide(2).subtract(2)));
        polygon.layoutYProperty().bind(pane.layoutYProperty().add(pane.heightProperty().divide(2).add(8)));
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
        Turn currentTurn = gameLogic.getCurrentPlayerProperty().get();
        TileState playerModel = null;
//        if (isMultiplayer) {
//            playerModel = receivePlayerModelMultiplayer(currentTurn);
//            if (currentTurn == Turn.PLAYER1) { // host
//                // send to client applyChanges?
//                playerModelGetter.getMultiplayerServer().sendInstructionsToClient("applyChanges");
//            } else { // client
//
//
//            }
//        } else {
            playerModel = receivePlayerModel(currentTurn);
        //}
        applyChanges(playerModel);
    }

    private TileState receivePlayerModel(Turn currentTurn) {
        return playerModelGetter.getPlayerModel(currentTurn);
    }

//    private TileState receivePlayerModelMultiplayer(Turn currentTurn) {
//        return playerModelGetter.getPlayerModelMultiplayer(currentTurn);
//    }

    private void applyChanges(TileState currentTileState) {
        if (currentTile.getTileState() == TileState.EMPTY) {
            gameLogic.clearError();
            currentTile.setTileState(currentTileState);
            gameLogic.switchTurn();
        } else {
            gameLogic.setErrorProperty("This tile is occupied.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindShapes();
    }
}
