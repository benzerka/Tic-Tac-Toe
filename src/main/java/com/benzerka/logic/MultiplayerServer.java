package com.benzerka.logic;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class MultiplayerServer {
    @Getter
    private int port;
    private ServerSocket serverSocket;
    private String clientResponse;
    private Runnable onClientBoundAction;
    @Getter
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public MultiplayerServer(Runnable onClientBoundAction) {
        this.onClientBoundAction = onClientBoundAction;
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
                // i dont know why this works
                System.out.println("Oczekiwanie na wiadomość od Klienta.");
                try {
                    if (Objects.nonNull(clientResponse = in.readLine())) {
                        if (clientResponse.equals("successfullyConnected")) {
                            // the game can now configure and launch :D
                            System.out.println("Klient wyslal mi wiadomosc :D");
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
