import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TCPServer extends Thread{

    private final static int PORT = 10008;
    private ServerSocket serverSocket;
    private ArrayList<TcpClientThread> clientList;

    public TCPServer(){
        clientList = new ArrayList<>();
    }

    public void run()
    {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                TcpClientThread client = new TcpClientThread (serverSocket.accept());
                client.start();
                clientList.add(client);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        /*finally
        {
            try {
                serverSocket.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }*/
    }

    public void sendMessageToAll(){
        for(TcpClientThread client: clientList){
            client.sendMessage("dc:"+client.user.getName());
        }
    }
}