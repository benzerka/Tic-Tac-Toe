package com.benzerka.logic.server;

import com.benzerka.gui.options.OptionsWindow;
import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Getter;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiplayerServerThread implements Runnable {
    private OptionsWindow optionsWindow;
    private Label portLabel;
    private Label ipLabel;
    private Runnable onClientBoundAction;
    @Getter
    private MultiplayerServer multiplayerServer;
    public MultiplayerServerThread(Label ipLabel, Label portLabel, Runnable onClientBoundAction, OptionsWindow optionsWindow) {
        this.ipLabel = ipLabel;
        this.portLabel = portLabel;
        this.onClientBoundAction = onClientBoundAction;
        this.optionsWindow = optionsWindow;
    }

    @Override
    public void run(){
        multiplayerServer = new MultiplayerServer(onClientBoundAction, optionsWindow);
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
