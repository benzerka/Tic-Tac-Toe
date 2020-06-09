package com.benzerka.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class GameLogic {
    private static TreeMap<String, String> tablica = new TreeMap<>();
    private static Boolean playerOne = true;
    private static byte sizeOfX;
    private static byte sizeOfY;
    private static byte amountOfElements;

    public static void addItem(String tile, String player) {
        tablica.put(tile, player);
    }

    public static String selectPlayer() {
        if (playerOne) {
            return "Player 1";
        } else {
            return "Player 2";
        }
    }

    public static Boolean getPlayerOne() {
        return playerOne;
    }

    public static void changePlayerTurn(Boolean playerOne) {
        GameLogic.playerOne = playerOne;
    }

    public static TreeMap<String, String> getTablica() {
        return tablica;
    }

    private static boolean isWinningCondition() {
        return false;
    }

    public static void checkWinningCondition() {

//        for (Map.Entry<String, String> entry : tablica.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//
//        }

        // iterate through all possible conditions
        // left to right, all rows
        // up to down, all rows
        // diagonal upper left corner to down right corner - with custom matrix it should also change the rows from left to right, down to up?
        // diagonal upper right corner to down left corner - ^^^

        // IT HAS TO RUN ONLY ONCE, IN INITIALIZE!!!
        // possible winning conditions tiles from left to right separated with row index
        Map<Byte, TreeSet<String>> allRowPossibilities = new HashMap<>();
        for (byte i = 0; i < sizeOfY; i++) {
            TreeSet<String> values = new TreeSet<>();
            for (int j = 0; j < sizeOfX; j++) {
                String tile = "tile" + (j + 1);
                values.add(tile);
            }
            allRowPossibilities.put(i, values);
        }

        // possible winning conditions from up to down separated with column index
        Map<Byte, TreeSet<String>> allColumnPossibilities = new HashMap<>();
        for (byte i = 0; i < sizeOfX; i++) {
            TreeSet<String> values = new TreeSet<>();
            for (byte j = i; j < (sizeOfX * sizeOfY); j = (byte) (j + sizeOfX)) {
                String tile = "tile" + (j + 1);
                values.add(tile);
            }
            allColumnPossibilities.put(i, values);
        }

        // possible winning diagonal conditions


        handleTie();
    }

    private static void drawWinningLine() {

    }

    public static void handleTie() {
        // check if it has exactly size x size elements in HashMap
        if (tablica.keySet().size() == (sizeOfX * sizeOfY)) {
            // check if there is no win condition
            //if (!isWinningCondition()) {
            System.out.println("most likely a tie");
            // if we have a tie, display it in a label? or pop up a new window saying that you or someone else has won, click ok to clear the stage or go back to menu
            //}
        }
    }

    public static void setAmountOfElements(byte elements) {
        GameLogic.amountOfElements = elements;
    }

    public static void setXSize(byte size) {
        GameLogic.sizeOfX = size;
    }

    public static void setYSize(byte size) {
        GameLogic.sizeOfY = size;
    }
}
