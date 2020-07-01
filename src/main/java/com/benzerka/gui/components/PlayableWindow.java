package com.benzerka.gui.components;

import com.benzerka.logic.GameLogic;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public interface PlayableWindow {
    GameLogic getGameLogic();
    GridPane getGameBoard();
    GridPane getMainScreen();
    VBox getMainScreenMenu();
}
