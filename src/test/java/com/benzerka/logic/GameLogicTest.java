package com.benzerka.logic;

import com.benzerka.gui.components.tile.Tile;
import com.benzerka.logic.events.WinListener;
import javafx.beans.property.ObjectProperty;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameLogicTest {
    @Test
    public void shouldCreateGameLogicWithEmptyBoard() {
        // given, when
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        // then
        ObjectProperty<TileState>[][] board;
        board = gameLogic.getGameBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TileState test = board[i][j].get();
                assertEquals(TileState.EMPTY, test);
            }
        }
    }

    @Test
    public void shouldClearGameBoard() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        // when
        gameLogic.getGameBoard()[1][1].setValue(TileState.CIRCLE);
        gameLogic.clearGameBoard();
        // then
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(TileState.EMPTY, gameLogic.getGameBoard()[i][j].get());
            }
        }
    }

//    @Test
//    public void shouldReturnCorrectWinnerWithoutChangingCurrentPlayerProperty() {
//        // given
//        GameLogic gameLogic = new GameLogic(3, 3, 3);
//        // when
//        gameLogic.setWinner();
//        // then
//        assertEquals("Player 1", gameLogic.getWinner());
//    }
//
//    @Test
//    public void shouldReturnCorrectWinnerWithChangingCurrentPlayerProperty() {
//        // given
//        GameLogic gameLogic = new GameLogic(3, 3, 3);
//        // when
//        gameLogic.getCurrentPlayerProperty().setValue(TileState.CROSS);
//        gameLogic.setWinner();
//        // then
//        assertEquals("Player 2", gameLogic.getWinner());
//    }

    @Test
    public void shouldHandleWinByMakingAHorizontalLineWhilePlacingTheLastElementOnLeftSide() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[2][1].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[2][2].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 0, 2);
        // then
        verify(winListener, times(1)).handleWin(0, 2, 2, 2, WinConditionType.HORIZONTAL);
    }

    @Test
    public void shouldHandleWinByMakingAHorizontalLineWhilePlacingTheLastElementInTheMiddle() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[2][0].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[2][2].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 1, 2);
        // then
        verify(winListener, times(1)).handleWin(0, 2, 2, 2, WinConditionType.HORIZONTAL);
    }

    @Test
    public void shouldHandleWinByMakingAHorizontalLineWhilePlacingTheLastElementOnRightSide() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[2][0].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[2][1].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 2, 2);
        // then
        verify(winListener, times(1)).handleWin(0, 2, 2, 2, WinConditionType.HORIZONTAL);
    }

    @Test
    public void shouldHandleWinByMakingAVerticalLineWhilePlacingTheLastElementOnTop() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[1][0].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[2][0].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 0, 0);
        // then
        verify(winListener, times(1)).handleWin(0, 0, 0, 2, WinConditionType.VERTICAL);
    }

    @Test
    public void shouldHandleWinByMakingAVerticalLineWhilePlacingTheLastElementInTheMiddle() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[0][0].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[2][0].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 0, 1);
        // then
        verify(winListener, times(1)).handleWin(0, 0, 0, 2, WinConditionType.VERTICAL);
    }

    @Test
    public void shouldHandleWinByMakingAVerticalLineWhilePlacingTheLastElementDown() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[0][0].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[1][0].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 0, 2);
        // then
        verify(winListener, times(1)).handleWin(0, 0, 0, 2, WinConditionType.VERTICAL);
    }

    @Test
    public void shouldHandleWinByMakingADiagonalUpperLeftDownRightLineWhilePlacingTheLastElementInUpperLeftCorner() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[1][1].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[2][2].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 0, 0);
        // then
        verify(winListener, times(1)).handleWin(0, 2, 0, 2, WinConditionType.DIAGONAL_UPPER_LEFT_DOWN_RIGHT);
    }

    @Test
    public void shouldHandleWinByMakingADiagonalUpperLeftDownRightLineWhilePlacingTheLastElementInTheMiddle() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[0][0].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[2][2].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 1, 1);
        // then
        verify(winListener, times(1)).handleWin(0, 2, 0, 2, WinConditionType.DIAGONAL_UPPER_LEFT_DOWN_RIGHT);
    }

    @Test
    public void shouldHandleWinByMakingADiagonalUpperLeftDownRightLineWhilePlacingTheLastElementInDownRightCorner() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[0][0].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[1][1].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 2, 2);
        // then
        verify(winListener, times(1)).handleWin(0, 2, 0, 2, WinConditionType.DIAGONAL_UPPER_LEFT_DOWN_RIGHT);
    }

    @Test
    public void shouldHandleWinByMakingADiagonalUpperRightDownLeftLineWhilePlacingTheLastElementInUpperRightCorner() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[1][1].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[2][0].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 2, 0);
        // then
        verify(winListener, times(1)).handleWin(2, 0, 0, 2, WinConditionType.DIAGONAL_UPPER_RIGHT_DOWN_LEFT);
    }

    @Test
    public void shouldHandleWinByMakingADiagonalUpperRightDownLeftLineWhilePlacingTheLastElementInTheMiddle() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[2][0].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[0][2].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 1, 1);
        // then
        verify(winListener, times(1)).handleWin(2, 0, 0, 2, WinConditionType.DIAGONAL_UPPER_RIGHT_DOWN_LEFT);
    }

    @Test
    public void shouldHandleWinByMakingADiagonalUpperRightDownLeftLineWhilePlacingTheLastElementInDownLeftCorner() {
        // given
        GameLogic gameLogic = new GameLogic(3, 3, 3);
        WinListener winListener = mock(WinListener.class);
        // when
        gameLogic.getGameBoard()[0][2].setValue(TileState.CIRCLE);
        gameLogic.getGameBoard()[1][1].setValue(TileState.CIRCLE);
        gameLogic.addWinListener(winListener);
        gameLogic.checkWinningCondition(TileState.CIRCLE, 0, 2);
        // then
        verify(winListener, times(1)).handleWin(2, 0, 0, 2, WinConditionType.DIAGONAL_UPPER_RIGHT_DOWN_LEFT);
    }
}