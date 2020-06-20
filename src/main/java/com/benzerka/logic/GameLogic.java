package com.benzerka.logic;

import com.benzerka.logic.events.TieListener;
import com.benzerka.logic.events.WinListener;
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

    private ObjectProperty<Turn> currentPlayerProperty;
    private StringProperty errorProperty;
    private List<TieListener> tieListeners = new ArrayList<>();
    private List<WinListener> winListeners = new ArrayList<>();
    private String winner;
    private int totalLeftRightElements;
    private int totalAboveDownElements;
    private int totalUpperLeftDownRightDiagonalElements;
    private int totalUpperRightDownLeftDiagonalElements;

    @SuppressWarnings("unchecked")
    public GameLogic(int boardXSize, int boardYSize, int winningConditionSize) {
        gameBoard = new SimpleObjectProperty[boardYSize][boardXSize];
        currentPlayerProperty = new SimpleObjectProperty<>(Turn.PLAYER1);
        errorProperty = new SimpleStringProperty("");
        this.boardXSize = boardXSize;
        this.boardYSize = boardYSize;
        this.winningConditionSize = winningConditionSize;
        initiateGameBoard();
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
        int leftelements = checkLeftTiles(type, x, y);
        int rightelements = checkRightTiles(type, x, y);
        totalLeftRightElements = 1 + leftelements + rightelements;

        int upelements = checkTilesAbove(type, x, y);
        int downelements = checkTilesBelow(type, x, y);
        totalAboveDownElements = 1 + upelements + downelements;

        int upperleftelements = checkUpperLeftDiagonalTiles(type, x, y);
        int downrightelements = checkDownRightDiagonalTiles(type, x, y);
        totalUpperLeftDownRightDiagonalElements = 1 + upperleftelements + downrightelements;

        int upperrightelements = checkUpperRightDiagonalTiles(type, x, y);
        int downleftelements = checkDownLeftDiagonalTiles(type, x, y);
        totalUpperRightDownLeftDiagonalElements = 1 + upperrightelements + downleftelements;

        if (totalLeftRightElements >= winningConditionSize) {
            handleWin(x - leftelements, x + rightelements, y, y, WinConditionType.HORIZONTAL);
        } else if (totalAboveDownElements >= winningConditionSize) {
            handleWin(x, x, y - upelements, y + downelements, WinConditionType.VERTICAL);
        } else if (totalUpperLeftDownRightDiagonalElements >= winningConditionSize) {
            handleWin(x - upperleftelements, x + downrightelements, y - upperleftelements, y + downrightelements, WinConditionType.DIAGONAL_UPPER_LEFT_DOWN_RIGHT);
        } else if (totalUpperRightDownLeftDiagonalElements >= winningConditionSize) {
            handleWin(x + upperrightelements, x - downleftelements, y - upperrightelements, y + downleftelements, WinConditionType.DIAGONAL_UPPER_RIGHT_DOWN_LEFT);
        } else {
            handleTie();
        }
    }

    private void handleWin(int startX, int endX, int startY, int endY, WinConditionType type) {
        setWinner();
        errorProperty.set(winner + " won!");
        this.winListeners.forEach((winListener) -> {
            winListener.handleWin(startX, endX, startY, endY, type);
        });
    }

    public void addTieListener(TieListener tieListener) {
        this.tieListeners.add(tieListener);
    }

    public void addWinListener(WinListener winListener) {
        this.winListeners.add(winListener);
    }

    private void handleTie() {
        if (isTie()) {
            errorProperty.set("There is a tie!");
            this.tieListeners.forEach(TieListener::handleTie);
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
        currentPlayerProperty.set((currentPlayerProperty.get() == Turn.PLAYER1) ? Turn.PLAYER2 : Turn.PLAYER1);
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

    public Turn getCurrentPlayer() {
        return currentPlayerProperty.get();
    }

    public ObjectProperty<Turn> getCurrentPlayerProperty() {
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
        currentPlayerProperty.setValue(Turn.PLAYER1);
    }

    public String getWinner() {
        return winner;
    }

    private void setWinner() {
        switch (currentPlayerProperty.get()) {
            case PLAYER1:
                winner = "Player 1";
                break;
            case PLAYER2:
                winner = "Player 2";
                break;
        }
    }
}
