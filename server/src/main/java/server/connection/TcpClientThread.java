package server.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import server.ExistingUsers;
import server.User;
import server.rooms.RoomController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class TcpClientThread extends Thread{
    private static Logger logger = LogManager.getLogger(TcpClientThread.class.getName());
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Map<String, User> existingUsers;
    private RoomController roomController;
    public User user;

    public TcpClientThread(Socket clientSocket) {
        this.roomController = new RoomController(this);
        this.user = new User();
        this.existingUsers = ExistingUsers.getInstance();
        this.clientSocket = clientSocket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
        } catch (IOException e) {
            logger.error(e.toString(), e);
        }
    }

    @Override
    public void run()
    {
        System.out.println ("New TCP Connection Started with " + clientSocket.getPort());
        String message;
        try {
            while ((message = in.readLine()) != null) {
                readMessage(message);
            }
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            //thread stopped
        }
        existingUsers.get(user.getConnectionId()).setConnection(false);
        for (User current : existingUsers.values()) {
            current.getThread().sendMessage(new JSONObject()
                    .put("msg", "disconnect")
                    .put("content", new JSONObject().put("name", user.getName())));
        }
        System.out.println("TCP Connection lost with " + clientSocket.getPort());
    }

    public void sendMessage(JSONObject message){
        out.println(message.toString());
    }

    private void readMessage(String message){
        JSONObject json = new JSONObject(message);
        JSONObject contentJSON = (JSONObject) json.get("content");

        switch (json.getString("msg")){
            case "udpPort":
                newUser(contentJSON.getInt("port"));
                break;
            case "newRoom":
                this.roomController.newRoom(this.user);
                break;
            case "joinRoom":
                this.roomController.joinRoom(this.user, contentJSON.getInt("id"));
                break;
            case "leaveRoom":
                this.roomController.leaveRoom(this.user);
                break;
            case "saveCharacter":
                this.roomController.saveCharacter(this.user, contentJSON.getString("character"));
                break;
            case "saveName":
                String a = contentJSON.getString("name");
                System.out.println(contentJSON.getString("name"));
                //this.roomController.saveName(this.user, contentJSON.getString("name"));
                break;
            case "refreshRoomList":
                this.roomController.refreshRoomList();
                break;
            case "startGame":
                this.roomController.startGame(this.user);
                break;
            case "getRoomList":
                this.roomController.getRoomList(this.user);
                break;
            default:
        }
    }

    private void newUser(int udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        this.user.setAddress(clientSocket.getInetAddress());
        this.user.setTcpPort(clientSocket.getPort());
        this.user.setUdpPort(udpPort);
        this.user.setConnectionId(id);
        this.user.setName("player"+ existingUsers.size());
        this.user.setCharacterType("Ground");

        existingUsers.put(id, this.user);
        existingUsers.get(user.getConnectionId()).setThread(this);
    }
}