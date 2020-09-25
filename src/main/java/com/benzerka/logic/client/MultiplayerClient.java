package com.benzerka.logic.client;

import com.benzerka.gui.multiplayer.MultiplayerWindow;
import com.benzerka.gui.options.OptionsWindow;
import com.benzerka.logic.TileState;
import javafx.application.Platform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class MultiplayerClient {
    private MultiplayerWindow multiplayerWindow;
    private OptionsWindow optionsWindow;
    private String serversResponse;
    private BufferedReader in;
    private PrintWriter out;
    private Socket clientSocket;
    private Runnable changeClientWindow;
    private int boardXSize;
    private int boardYSize;
    private int winningCondition;

    public MultiplayerClient(String ip, int port, Runnable didConnect, Runnable didNotConnect, Runnable changeClientWindow, OptionsWindow optionsWindow, MultiplayerWindow multiplayerWindow) {
        try (Socket clientSocket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            this.clientSocket = clientSocket;
            this.in = in;
            this.out = out;
            this.changeClientWindow = changeClientWindow;
            this.optionsWindow = optionsWindow;
            this.multiplayerWindow = multiplayerWindow;
            Thread thread = new Thread(didConnect);
            thread.start();
            listenForServerInstructions();

        } catch (IOException e) {
            Thread thread = new Thread(didNotConnect);
            thread.start();
        }
    }

    public void listenForServerInstructions() {
        System.out.println("Client: czekam na instrukcje serwera");
        while (clientSocket.isBound()) {
            try {
                if (Objects.nonNull(serversResponse = in.readLine())) {
                    if (serversResponse.equals("startGame")) {
                        new Thread(changeClientWindow).start();
                        out.println("successfullyConnected");
                        //break;
                    }
                    if (serversResponse.equals(extractInitializationValues(serversResponse))) {
                        Platform.runLater(() -> {
                            multiplayerWindow.initializeGame(boardXSize, boardYSize, winningCondition);
                        });
                        //break;
                    }
                    if (serversResponse.equals(figureOutClientPlayerModel(serversResponse))) {
                        TileState hostModel = TileState.valueOf(serversResponse);
                        if (optionsWindow.getMultiplayerPlayerModel() == hostModel) {
                            optionsWindow.setMultiplayerClientModelInPlayerModelGetterClass(pickRandomModelExcept(hostModel));
                        } else {
                            optionsWindow.setMultiplayerClientModelInPlayerModelGetterClass(hostModel);
                        }
                        //break;
                    }
                    if (serversResponse.equals("getOptionsPlayerModel")) {
                        // wyslac player model do hosta
                        TileState clientModel = optionsWindow.getMultiplayerPlayerModel();
                        out.println(clientModel);
                        //break;
                    }
                    if (serversResponse.equals("getNickname")) {
                        out.println("clientNickname:" + optionsWindow.getMultiplayerHostName());
                        //break;
                    }
                    if (serversResponse.equals("disconnect")) {
                        out.println("successfullyDisconnected");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private TileState pickRandomModelExcept(TileState clientModel) {
        TileState[] allModels = TileState.class.getEnumConstants();
        for (TileState model : allModels) {
            if (clientModel != model && model != TileState.EMPTY) {
                return model;
            }
        }
        // unreachable statement required to be placed
        return null;
    }

    private String figureOutClientPlayerModel(String clientResponse) {
        // TODO: slaby motyw z manualnie wypisywanymi stringami, lepiej uzyć już istniejącego TileState
        if (clientResponse.equals("CIRCLE") || clientResponse.equals("CROSS") || clientResponse.equals("DIAMOND") || clientResponse.equals("PENTAGON") || clientResponse.equals("STAR") || clientResponse.equals("TRIANGLE")) {
            return clientResponse;
        }
        // unreachable statement required to be placed
        return "0";
    }

    private String extractInitializationValues(String serversResponse) {
        String[] values = serversResponse.split(",");
        if (values.length != 3) {
            return null;
        }
        int[] valuesAsInt = new int[3];
        for (int i = 0; i < values.length; i++) {
            valuesAsInt[i] = Integer.valueOf(values[i]);
        }
        boardXSize = valuesAsInt[0];
        boardYSize = valuesAsInt[1];
        winningCondition = valuesAsInt[2];
        return boardXSize + "," + boardYSize + "," + winningCondition;
    }
}