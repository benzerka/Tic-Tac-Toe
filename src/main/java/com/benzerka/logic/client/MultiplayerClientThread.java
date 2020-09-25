package com.benzerka.logic.client;

import com.benzerka.gui.multiplayer.MultiplayerWindow;
import com.benzerka.gui.options.OptionsWindow;

public class MultiplayerClientThread implements Runnable {
    private MultiplayerWindow multiplayerWindow;
    private OptionsWindow optionsWindow;
    private String ip;
    private int port;
    private Runnable didConnect;
    private Runnable didNotConnect;
    private Runnable changeClientWindow;
    //private MultiplayerVariablesHandler multiplayerVariablesHandler;

    public MultiplayerClientThread(String ip, int port, Runnable didConnect, Runnable didNotConnect, Runnable changeClientWindow, OptionsWindow optionsWindow, MultiplayerWindow multiplayerWindow) {
        this.ip = ip;
        this.port = port;
        this.didConnect = didConnect;
        this.didNotConnect = didNotConnect;
        this.changeClientWindow = changeClientWindow;
        this.optionsWindow = optionsWindow;
        this.multiplayerWindow = multiplayerWindow;
    }

    @Override
    public void run() {
        new MultiplayerClient(ip, port, didConnect, didNotConnect, changeClientWindow, optionsWindow, multiplayerWindow);
    }
}
