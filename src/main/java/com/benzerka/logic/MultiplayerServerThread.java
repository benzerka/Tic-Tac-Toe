package com.benzerka.logic;

import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Getter;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiplayerServerThread implements Runnable {
    private Label portLabel;
    private Label ipLabel;
    private Runnable onClientBoundAction;
    @Getter
    private MultiplayerServer multiplayerServer;
    public MultiplayerServerThread(Label ipLabel, Label portLabel, Runnable onClientBoundAction) {
        this.ipLabel = ipLabel;
        this.portLabel = portLabel;
        this.onClientBoundAction = onClientBoundAction;
    }

    @Override
    public void run(){
        multiplayerServer = new MultiplayerServer(onClientBoundAction);
        Platform.runLater(() -> {
            portLabel.setText(String.valueOf(multiplayerServer.getPort()));
            try {
                ipLabel.setText(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            portLabel.setVisible(true);
            ipLabel.setVisible(true);
        });
        multiplayerServer.acceptClient();
    }
}
