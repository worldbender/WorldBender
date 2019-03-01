package server.connection;

import server.Properties;
import server.User;
import server.rooms.Room;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class TcpServer extends Thread{
    private final static int PORT = Integer.parseInt(Properties.loadConfigFile("PortTcp"));
    private ServerSocket serverSocket;
    private static Thread senderThread;
    private static GameController sender;

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

    public static void createGameController(Room room){
        sender = new GameController(room);
        room.setGameController(sender);
        senderThread = new Thread(sender);
        senderThread.start();
    }

    public static void sendTcpMsgToAllUsers(String msg, Map<String, User> existingUsers){
        for (User current : existingUsers.values()) {
            current.getThread().sendMessage(msg);
        }
    }

    public static void sendTcpMsgToAllUsersInRoom(String msg, CopyOnWriteArrayList<User> usersInRoom){
        for (User current : usersInRoom) {
            current.getThread().sendMessage(msg);
        }
    }
}