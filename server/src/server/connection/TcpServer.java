package server.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import server.Properties;
import server.User;
import server.rooms.Room;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class TcpServer extends Thread{
    private static Logger logger = LogManager.getLogger(TcpServer.class.getName());
    private static final int PORT = Integer.parseInt(Properties.loadConfigFile("PORT_TCP"));
    private static Thread senderThread;
    private static GameController sender;

    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true) {
                TcpClientThread client = new TcpClientThread (serverSocket.accept());
                client.start();
            }
        }
        catch (IOException e) {
            logger.error(e.toString(), e);
        }
    }

    public static void createGameController(Room room){
        sender = new GameController(room);
        room.setGameController(sender);
        senderThread = new Thread(sender);
        senderThread.start();
    }

    public static void sendTcpMsgToAllUsers(JSONObject msg, Map<String, User> existingUsers){
        for (User current : existingUsers.values()) {
            current.getThread().sendMessage(msg);
        }
    }

    public static void sendTcpMsgToAllUsersInRoom(JSONObject msg, CopyOnWriteArrayList<User> usersInRoom){
        for (User current : usersInRoom) {
            current.getThread().sendMessage(msg);
        }
    }
}