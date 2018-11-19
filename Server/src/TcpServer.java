import java.io.*;
import java.net.ServerSocket;
import java.util.Map;

public class TcpServer extends Thread{

    private final static int PORT = 10008;
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
}