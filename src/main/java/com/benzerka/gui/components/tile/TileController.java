package com.benzerka.gui.components.tile;

import com.benzerka.logic.GameLogic;
import com.benzerka.singleplayer.SingleplayerController;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    public void switchPane(MouseEvent mouseEvent) {
        // If current player is player one:
        if (GameLogic.getPlayerOne()) {
            applyChanges(true);
        } else {
            applyChanges(false);
        }
    }

    private void applyChanges(boolean playerOne) {
        // get current Tile id
        String tileIndex = currentTile.getId();

        // disable the possibility to change already existing tile in a TreeMap
        // if TreeMap contains current tile's id
        if (GameLogic.getTablica().keySet().contains(tileIndex)) {
            // tell the user that he cannot override already existing tile.
            SingleplayerController.getErrorLabel().setText("This tile is occupied.");
        } else {
            // clear error Label
            SingleplayerController.getErrorLabel().setText("");
            // Player's turn: show either circle or cross on a tile.
            emptyPane.setVisible(false);
            crossPane.setVisible(!playerOne);
            circlePane.setVisible(playerOne);

            // add current tile's id (position on a map) with current player's turn as a value into TreeMap
            GameLogic.addItem(tileIndex, GameLogic.selectPlayer());

            // set the turn to the other player.
            GameLogic.changePlayerTurn(!playerOne);

            // display updated player's turn in a label.
            SingleplayerController.getPlayer().setText(GameLogic.selectPlayer());

            // check winning condition? not sure if it should be here or somewhere else
            GameLogic.checkWinningCondition();
            // inside winning condition draw a line corresponding to the line that won, draw more if there are more winning conditions
        }
    }
}
