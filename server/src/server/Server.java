package server;

import com.badlogic.gdx.ApplicationListener;
import server.connection.TcpServer;
import server.connection.UdpServer;
import java.io.IOException;

class Server implements ApplicationListener {
    public Server(){

    }
    @Override
    public void create() {
        TcpServer tcpServer;
        UdpServer udpServer;
        try {
            udpServer = new UdpServer();
            tcpServer = new TcpServer();
            tcpServer.start();
            udpServer.start();
            System.out.println("Server is running.");
        } catch (IOException e) {
            System.out.println("Porty zajęte, prawdopodobnie serwer już jest włączony!");
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}