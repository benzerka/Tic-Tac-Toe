package com.benzerka.logic;

public enum TileState {
    EMPTY(null), CROSS("/cross.png"), CIRCLE("/circle.png"), TRIANGLE("/triangle.png"), STAR("/star.png");

    String iconPath;

    TileState(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }
}
