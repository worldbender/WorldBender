package server.connection;

import server.Properties;
import server.User;
import RoomsController.Room;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

public class TcpServer extends Thread{
    private final static int PORT = Integer.parseInt(Properties.loadConfigFile("PortTcp"));
    private ServerSocket serverSocket;
    private static Thread senderThread;
    private static GameController sender;

    public TcpServer(){
//        sender = new GameController();
//        senderThread = new Thread(sender);
//        senderThread.start();
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

    public static void createGameController(Room room){
        sender = new GameController(room);
        senderThread = new Thread(sender);
        senderThread.start();
    }

    public static void sendTcpMsgToAllUsers(String msg, Map<String, User> existingUsers){
        for (User current : existingUsers.values()) {
            current.getThread().sendMessage(msg);
        }
    }

    public static void sendTcpMsgToAllUsersInRoom(String msg, Room room){
        for (User current : room.getUsersInRoom()) {
            current.getThread().sendMessage(msg);
        }
    }
}