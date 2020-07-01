package com.benzerka.logic;

import lombok.Getter;

@Getter
public enum TileState {
    EMPTY(null), CROSS("/cross.png"), CIRCLE("/circle.png"), TRIANGLE("/triangle.png"), STAR("/star.png"), DIAMOND("/diamond.png"), PENTAGON("/pentagon.png");

    String iconPath;

    TileState(String iconPath) {
        this.iconPath = iconPath;
    }
}
