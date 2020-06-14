package com.benzerka.logic;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private ObjectProperty<TileState>[][] gameBoard;

    private int boardXSize;
    private int boardYSize;
    private int winningConditionSize;
    private ObjectProperty<TileState> currentPlayerProperty;
    private StringProperty errorProperty;
    private List<Runnable> tieListeners = new ArrayList<>();
    private List<Runnable> winListeners = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public GameLogic(int boardXSize, int boardYSize, int winningConditionSize) {
        gameBoard = new SimpleObjectProperty[boardYSize][boardXSize];
        currentPlayerProperty = new SimpleObjectProperty<>(setFirstPlayer());
        errorProperty = new SimpleStringProperty("");
        this.boardXSize = boardXSize;
        this.boardYSize = boardYSize;
        this.winningConditionSize = winningConditionSize;
        initiateGameBoard();
    }

    public GameLogic() {
    }

    private TileState setFirstPlayer() {
        return TileState.CIRCLE;
    }

    private void initiateGameBoard() {
        for (int i = 0; i < boardYSize; i++) {
            for (int j = 0; j < boardXSize; j++) {
                gameBoard[i][j] = new SimpleObjectProperty<>(TileState.EMPTY);
            }
        }
    }

    public void clearGameBoard() {
        for (int i = 0; i < boardYSize; i++) {
            for (int j = 0; j < boardXSize; j++) {
                gameBoard[i][j].setValue(TileState.EMPTY);
            }
        }
    }

    public void checkWinningCondition(TileState type, int x, int y) {
        int totalLeftRightElements = 1 + checkLeftTiles(type, x, y) + checkRightTiles(type, x, y);
        int totalAboveDownElements = 1 + checkTilesAbove(type, x, y) + checkTilesBelow(type, x, y);
        int totalUpperLeftDownRightDiagonalElements = 1 + checkUpperLeftDiagonalTiles(type, x, y) + checkDownRightDiagonalTiles(type, x, y);
        int totalUpperRightDownLeftDiagonalElements = 1 + checkUpperRightDiagonalTiles(type, x, y) + checkDownLeftDiagonalTiles(type, x, y);
        if (totalLeftRightElements >= winningConditionSize ||
                totalAboveDownElements >= winningConditionSize ||
                totalUpperLeftDownRightDiagonalElements >= winningConditionSize ||
                totalUpperRightDownLeftDiagonalElements >= winningConditionSize) {
            handleWin(type);
        } else {
            handleTie();
        }
    }

    public void addTieListener(Runnable listener) {
        this.tieListeners.add(listener);
    }

    public void addWinListener(Runnable listener) {
        this.winListeners.add(listener);
    }

    private void handleTie() {
        if (isTie()) {
            errorProperty.set("There is a tie!");
            this.tieListeners.forEach(Runnable::run);
        }
    }

    private boolean isTie() {
        int counter = 0;
        for (int i = 0; i < boardYSize; i++) {
            for (int j = 0; j < boardXSize; j++) {
                if (gameBoard[i][j].get() != TileState.EMPTY) {
                    counter++;
                }
            }
        }
        return counter == (boardXSize * boardYSize);
    }

    private void handleWin(TileState type) {
        errorProperty.set(type + " won!");
        this.winListeners.forEach(Runnable::run);
    }

    private int checkLeftTiles(TileState type, int x, int y) {
        // exit condition out of recursive function, prevents going out of bounds.
        if (x == 0) {
            return 0;
        }
        // check if there are any elements of the same type on the left side of current element position
        if (gameBoard[y][x - 1].get() == type) {
            return 1 + checkLeftTiles(type, x - 1, y);
        } else {
            return 0;
        }
    }

    private int checkRightTiles(TileState type, int x, int y) {
        if (x == gameBoard[0].length - 1) {
            return 0;
        }
        if (gameBoard[y][x + 1].get() == type) {
            return 1 + checkRightTiles(type, x + 1, y);
        } else {
            return 0;
        }
    }

    private int checkTilesAbove(TileState type, int x, int y) {
        if (y == 0) {
            return 0;
        }
        if (gameBoard[y - 1][x].get() == type) {
            return 1 + checkTilesAbove(type, x, y - 1);
        } else {
            return 0;
        }
    }

    private int checkTilesBelow(TileState type, int x, int y) {
        if (y == gameBoard.length - 1) {
            return 0;
        }
        if (gameBoard[y + 1][x].get() == type) {
            return 1 + checkTilesBelow(type, x, y + 1);
        } else {
            return 0;
        }
    }

    private int checkUpperLeftDiagonalTiles(TileState type, int x, int y) {
        if (x == 0 || y == 0) {
            return 0;
        }
        if (gameBoard[y - 1][x - 1].get() == type) {
            return 1 + checkUpperLeftDiagonalTiles(type, x - 1, y - 1);
        } else {
            return 0;
        }
    }

    private int checkDownRightDiagonalTiles(TileState type, int x, int y) {
        if (x == gameBoard[0].length - 1 || y == gameBoard.length - 1) {
            return 0;
        }
        if (gameBoard[y + 1][x + 1].get() == type) {
            return 1 + checkDownRightDiagonalTiles(type, x + 1, y + 1);
        } else {
            return 0;
        }
    }

    private int checkUpperRightDiagonalTiles(TileState type, int x, int y) {
        if (x == gameBoard[0].length - 1 || y == 0) {
            return 0;
        }
        if (gameBoard[y - 1][x + 1].get() == type) {
            return 1 + checkUpperRightDiagonalTiles(type, x + 1, y - 1);
        } else {
            return 0;
        }
    }

    private int checkDownLeftDiagonalTiles(TileState type, int x, int y) {
        if (y == gameBoard.length - 1 || x == 0) {
            return 0;
        }
        if (gameBoard[y + 1][x - 1].get() == type) {
            return 1 + checkDownLeftDiagonalTiles(type, x - 1, y + 1);
        } else {
            return 0;
        }
    }

    public void switchTurn() {
        if (currentPlayerProperty.get() == TileState.CROSS) {
            currentPlayerProperty.set(TileState.CIRCLE);
        } else if (currentPlayerProperty.get() == TileState.CIRCLE) {
            currentPlayerProperty.set(TileState.CROSS);
        } else {
            currentPlayerProperty.set(TileState.EMPTY);
        }
    }

    public ObjectProperty<TileState>[][] getGameBoard() {
        return gameBoard;
    }

    public int getBoardXSize() {
        return boardXSize;
    }

    public int getBoardYSize() {
        return boardYSize;
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

    public void resetPlayer() {
        currentPlayerProperty.setValue(setFirstPlayer());
    }
}
