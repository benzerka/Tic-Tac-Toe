package com.benzerka.logic.events;

import com.benzerka.logic.WinConditionType;

public interface WinListener {
    void handleWin(int startX, int endX, int startY, int endY, WinConditionType type);
}
