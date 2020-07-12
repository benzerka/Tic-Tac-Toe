package com.benzerka.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class MultiplayerClient {
    private String serversResponse;
    private BufferedReader in;
    private PrintWriter out;
    private Socket clientSocket;
    private Runnable changeClientWindow;

    public MultiplayerClient(String ip, int port, Runnable didConnect, Runnable didNotConnect, Runnable changeClientWindow) {
        try (Socket clientSocket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            this.clientSocket = clientSocket;
            this.in = in;
            this.out = out;
            this.changeClientWindow = changeClientWindow;
            Thread thread = new Thread(didConnect);
            thread.start();
            listenForServerInstructions();

        } catch (IOException e) {
            Thread thread = new Thread(didNotConnect);
            thread.start();
        }
    }

    public void listenForServerInstructions() {
        while (clientSocket.isBound()) {
            try {
                if (Objects.nonNull(serversResponse = in.readLine())) {
                    if (serversResponse.equals("startGame")) {
                        Thread thread = new Thread(changeClientWindow);
                        thread.start();
                        out.println("successfullyConnected");
                        //break;
                    }
                    if (serversResponse.equals("3,3,3")) {
                        System.out.println("x 3 y 3 w 3");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}