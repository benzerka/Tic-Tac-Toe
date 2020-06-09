package com.benzerka.singleplayer;

import com.benzerka.gui.components.tile.Tile;
import com.benzerka.logic.GameLogic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleplayerController implements Initializable {
    @FXML
    public Tile tile1;

    @FXML
    public Tile tile2;

    @FXML
    public Tile tile3;

    @FXML
    public Tile tile4;

    @FXML
    public Tile tile5;

    @FXML
    public Tile tile6;

    @FXML
    public Tile tile7;

    @FXML
    public Tile tile8;

    @FXML
    public Tile tile9;

    public static Label getPlayer() {
        return playerStatic;
    }

    private static Label playerStatic;

    @FXML
    public Label player;

    public static Label getErrorLabel() {
        return errorLabelStatic;
    }

    private static Label errorLabelStatic;
    @FXML
    public Label errorLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player.setText(GameLogic.selectPlayer());
        playerStatic = player;
        errorLabelStatic = errorLabel;
        // display some window asking user for board/map size
        // set amount of elements required to win
        GameLogic.setAmountOfElements((byte)3);
        // set board size
        GameLogic.setXSize((byte)3);
        GameLogic.setYSize((byte)3);
    }
}
