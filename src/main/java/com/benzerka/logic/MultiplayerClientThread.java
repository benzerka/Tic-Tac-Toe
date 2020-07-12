package com.benzerka.logic;

public class MultiplayerClientThread implements Runnable{
    private String ip;
    private int port;
    private Runnable didConnect;
    private Runnable didNotConnect;
    private Runnable changeClientWindow;

    public MultiplayerClientThread(String ip, int port, Runnable didConnect, Runnable didNotConnect, Runnable changeClientWindow) {
        this.ip = ip;
        this.port = port;
        this.didConnect = didConnect;
        this.didNotConnect = didNotConnect;
        this.changeClientWindow = changeClientWindow;
    }

    @Override
    public void run(){
        new MultiplayerClient(ip, port, didConnect, didNotConnect, changeClientWindow);
    }
}
