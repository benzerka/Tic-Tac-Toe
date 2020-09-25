package com.benzerka.logic.server;

import com.benzerka.gui.options.OptionsWindow;
import com.benzerka.logic.TileState;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class MultiplayerServer {
    private OptionsWindow optionsWindow;
    @Getter
    private int port;
    private ServerSocket serverSocket;
    private String clientResponse;
    private Runnable onClientBoundAction;
    @Getter
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public MultiplayerServer(Runnable onClientBoundAction, OptionsWindow optionsWindow) {
        this.onClientBoundAction = onClientBoundAction;
        this.optionsWindow = optionsWindow;
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            this.serverSocket = serverSocket;
            port = serverSocket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptClient() {
        try (Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            this.clientSocket = clientSocket;
            this.out = out;
            this.in = in;
            Thread thread = new Thread(onClientBoundAction);
            thread.start();
            while (clientSocket.isBound()) {
                try {
                    if (Objects.nonNull(clientResponse = in.readLine())) {
                        if (clientResponse.equals("successfullyConnected")) {
                            // the game can now configure and launch :D
                            //break;
                        }
                        if (clientResponse.equals(figureOutClientPlayerModel(clientResponse))) {
                            TileState clientModel = TileState.valueOf(clientResponse);
                            if (optionsWindow.getMultiplayerPlayerModel() == clientModel) {
                                optionsWindow.setMultiplayerClientModelInPlayerModelGetterClass(pickRandomModelExcept(clientModel));
                            } else {
                                optionsWindow.setMultiplayerClientModelInPlayerModelGetterClass(clientModel);
                            }
                            //break;
                        }
                        if (clientResponse.matches("^clientNickname:.+")) {
                            String[] splitResponse = clientResponse.split(":");
                            String clientNickname = splitResponse[1];
                            if (optionsWindow.getMultiplayerHostName().equals(clientNickname)) {
                                if (clientNickname.equals("Player")) {
                                    clientNickname = "Connected Player";
                                } else {
                                    // else raczej nie jest tu konieczny
                                    clientNickname = "Player";
                                }
                            }
                            // KLIENT NICKNAME
                            optionsWindow.setClientNickname(clientNickname);
                            // TODO: set the nickname in hosts window: TURN and alert requires nickname
                            //break;
                        }
                        if (clientResponse.equals("successfullyDisconnected")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public void sendInstructionsToClient(String msg) {
        out.println(msg);
    }

    public void stopServer() {
        try {
            if (Objects.nonNull(in)) {
                in.close();
            }
            if (Objects.nonNull(out)) {
                out.close();
            }
            if (Objects.nonNull(clientSocket)) {
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
