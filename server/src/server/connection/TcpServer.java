package server.connection;


import server.Properties;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpServer extends Thread{

    private final static int PORT = Integer.parseInt(Properties.loadConfigFile("PortTcp"));
    private ServerSocket serverSocket;

    public TcpServer(){

    }

    public void run()
    {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                TcpClientThread client = new TcpClientThread (serverSocket.accept());
                client.start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                serverSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}