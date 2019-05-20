package server.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import server.Properties;
import server.User;
import server.rooms.Room;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Map;

public class TcpServer extends Thread{
    private Properties properties;
    private Logger logger;
    private final int PORT;
    private static Thread senderThread;
    private static GameController sender;
    
    public TcpServer() {
        properties = new Properties();
        this.PORT =  Integer.parseInt(properties.loadConfigFile("PORT_TCP"));
        this.logger = LogManager.getLogger(TcpServer.class.getName());
        
    }
    
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

    public static void sendTcpMsgToAllUsersInRoom(JSONObject msg, List<User> usersInRoom){
        for (User current : usersInRoom) {
            current.getThread().sendMessage(msg);
        }
    }
}