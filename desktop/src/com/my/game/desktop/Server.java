package com.my.game.desktop;

import com.badlogic.gdx.Game;
import java.io.IOException;

class Server extends Game {
    public Server(){

    }
    @Override
    public void create() {
        TcpServer tcpServer = new TcpServer();
        tcpServer.start();

        UdpServer udpServer = null;
        try {
            udpServer = new UdpServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        udpServer.start();
    }
}