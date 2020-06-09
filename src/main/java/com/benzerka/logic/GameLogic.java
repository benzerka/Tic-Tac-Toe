package com.benzerka.logic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class GameLogic {
    private static GameLogic INSTANCE;
    private TileState[][] gameBoard;

    private final int boardSize;
    private final int winningConditionSize;
    private ObjectProperty<TileState> currentPlayerProperty;
    private StringProperty errorProperty;

    private GameLogic(int boardSize, int winningConditionSize) {
        gameBoard = new TileState[boardSize][boardSize];
        currentPlayerProperty = new SimpleObjectProperty<>(TileState.CIRCLE);
        errorProperty = new SimpleStringProperty("");
        this.boardSize = boardSize;
        this.winningConditionSize = winningConditionSize;
        clearGameBoard();
    }

    public static GameLogic getInstance() {
        return getInstance(3, 3);
    }

    public static GameLogic getInstance(int boardSize, int winningConditionSize) {
        if (Objects.isNull(INSTANCE)) {
            INSTANCE = new GameLogic(boardSize, winningConditionSize);
        }
        return INSTANCE;
    }

    private void clearGameBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gameBoard[i][j] = TileState.EMPTY;
            }
        }
    }

    public void checkWinningCondition() {
        for(int i = 0; i<boardSize; i++) {
            int crossesAmount = 0;
            int circlesAmount = 0;
            for(int j =0; j<boardSize; j++) {
                if(gameBoard[i][j] == TileState.CROSS) {
                    crossesAmount++;
                } else if(gameBoard[i][j] == TileState.CIRCLE) {
                    circlesAmount++;
                }
            }
            if(crossesAmount == winningConditionSize) {
                errorProperty.set("CROSS won");
            } else if(circlesAmount == winningConditionSize) {
                errorProperty.set("CIRCLE won");
            }
        }
    }

    public void switchTurn() {
        if (currentPlayerProperty.get() == TileState.CROSS) {
            currentPlayerProperty.set(TileState.CIRCLE);
        } else {
            currentPlayerProperty.set(TileState.CROSS);
        }
    }

    public TileState[][] getGameBoard() {
        return gameBoard;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public TileState getCurrentPlayer() {
        return currentPlayerProperty.get();
    }

    public ObjectProperty<TileState> getCurrentPlayerProperty() {
        return currentPlayerProperty;
    }

    public StringProperty getErrorProperty() {
        return errorProperty;
    }

    public void clearError() {
        errorProperty.set("");
    }

    public void setError(String error) {
        errorProperty.set(error);
    }

    public void setTile(int row, int column, TileState tileState) {
        gameBoard[row][column] = tileState;
    }
}
