package com.benzerka.gui.components;

import com.benzerka.logic.WinConditionType;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

public class LineCreator {
    private GridPane gameBoard;
    private WinConditionType type;

    // actual position in Pane
    private int tileStartX;
    private int tileStartY;
    private int tileEndX;
    private int tileEndY;

    // location
    private int gameBoardStartX;
    private int gameBoardStartY;
    private int gameBoardEndX;
    private int gameBoardEndY;
    private Line winLine;


    public void sendTileXPosition(int tileStartX, int tileEndX) {
        this.tileStartX = tileStartX;
        this.tileEndX = tileEndX;
    }

    public void sendTileYPosition(int tileStartY, int tileEndY) {
        this.tileStartY = tileStartY;
        this.tileEndY = tileEndY;
    }

    private void createWinLine() {
        winLine = new Line(gameBoardStartX, gameBoardStartY, gameBoardEndX, gameBoardEndY);
        winLine.setStrokeWidth(3);
    }

    public void drawWinLine() {
        getGameBoardElementsPosition();
        createWinLine();
        setWinLineOnGameBoard();
    }

    private void getGameBoardElementsPosition() {
        ObservableList<Node> childrens = gameBoard.getChildren();

        for (Node tile : childrens) {
            // start tile
            if (GridPane.getRowIndex(tile) == tileStartY && GridPane.getColumnIndex(tile) == tileStartX) {
                switch (type) {
                    case HORIZONTAL:
                        gameBoardStartX = (int) (tile.getLayoutX() - (tile.getBoundsInParent().getWidth() / 2) + 3);
                        gameBoardStartY = (int) tile.getLayoutY();
                        break;
                    case VERTICAL:
                        gameBoardStartX = (int) tile.getLayoutX();
                        gameBoardStartY = (int) (tile.getLayoutY() - (tile.getBoundsInParent().getHeight() / 2) + 3);
                        break;
                    case DIAGONAL_UPPER_LEFT_DOWN_RIGHT:
                        gameBoardStartX = (int) (tile.getLayoutX() - (tile.getLayoutX() - tile.getBoundsInParent().getMinX()) + 1);
                        gameBoardStartY = (int) (tile.getLayoutY() - (tile.getLayoutY() - tile.getBoundsInParent().getMinY()) + 1);
                        break;
                    case DIAGONAL_UPPER_RIGHT_DOWN_LEFT:
                        gameBoardStartX = (int) (tile.getLayoutX() + (tile.getBoundsInParent().getMaxX() - tile.getLayoutX()) - 5);
                        gameBoardStartY = (int) (tile.getLayoutY() - (tile.getLayoutY() - tile.getBoundsInParent().getMinY()) + 1);
                        break;
                }
            }
            // end tile
            if (GridPane.getRowIndex(tile) == tileEndY && GridPane.getColumnIndex(tile) == tileEndX) {
                switch (type) {
                    case HORIZONTAL:
                        gameBoardEndX = (int) (tile.getLayoutX() + (tile.getBoundsInParent().getWidth() / 2) - 4);
                        gameBoardEndY = (int) tile.getLayoutY();
                        break;
                    case VERTICAL:
                        gameBoardEndX = (int) tile.getLayoutX();
                        gameBoardEndY = (int) (tile.getLayoutY() + (tile.getBoundsInParent().getHeight() / 2) - 4);
                        break;
                    case DIAGONAL_UPPER_LEFT_DOWN_RIGHT:
                        gameBoardEndX = (int) (tile.getLayoutX() + (tile.getBoundsInParent().getMaxX() - tile.getLayoutX()) - 6);
                        gameBoardEndY = (int) (tile.getLayoutY() + (tile.getBoundsInParent().getMaxY() - tile.getLayoutY()) - 6);
                        break;
                    case DIAGONAL_UPPER_RIGHT_DOWN_LEFT:
                        gameBoardEndX = (int) (tile.getLayoutX() - (tile.getLayoutX() - tile.getBoundsInParent().getMinX()) + 1);
                        gameBoardEndY = (int) (tile.getLayoutY() + (tile.getBoundsInParent().getMaxY() - tile.getLayoutY()) - 5);
                        break;
                }
            }
        }
    }

    private void setWinLineOnGameBoard() {
        switch (type) {
            case HORIZONTAL:
                gameBoard.add(winLine, tileStartX, tileStartY, tileEndX - tileStartX + 1, 1);
                break;
            case VERTICAL:
                gameBoard.add(winLine, tileStartX, tileStartY, 1, tileEndY - tileStartY + 1);
                break;
            case DIAGONAL_UPPER_LEFT_DOWN_RIGHT:
                gameBoard.add(winLine, tileStartX, tileStartY, tileEndX - tileStartX + 1, tileEndY - tileStartY + 1);
                break;
            case DIAGONAL_UPPER_RIGHT_DOWN_LEFT:
                gameBoard.add(winLine, tileEndX, tileStartY, tileStartX - tileEndX + 1, tileEndY - tileStartY + 1);
                break;
        }
    }

    public void sendGameBoard(GridPane gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void clearLines() {
        gameBoard.getChildren().remove(winLine);
    }

    public void setType(WinConditionType type) {
        this.type = type;
    }
}
