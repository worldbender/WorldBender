package server.connection;

import org.json.JSONObject;
import server.rooms.Room;
import server.rooms.RoomList;
import com.badlogic.gdx.Gdx;
import server.ExistingUsers;
import server.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class TcpClientThread extends Thread{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Map<String, User> existingUsers;
    private List<Room> rooms;
    public User user;

    public TcpClientThread(Socket clientSocket) {
        this.user = new User();
        this.existingUsers = ExistingUsers.getInstance();
        this.clientSocket = clientSocket;
        this.rooms = RoomList.getInstance();
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        } catch (IOException e) { }
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

    public void readMessage(String message){
        JSONObject json = new JSONObject(message);
        JSONObject contentJSON = (JSONObject) json.get("content");

        switch (json.getString("msg")){
            case "udpPort": newUser(contentJSON.getInt("port")); break;
            case "newRoom": newRoom(); break;
            case "joinRoom": joinRoom(contentJSON.getInt("id")); break;
            case "leaveRoom": leaveRoom(); break;
            case "startGame": startGame(); break;
        }
    }

    public void newUser(int udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        this.user.setAddress(clientSocket.getInetAddress());
        this.user.setTcpPort(clientSocket.getPort());
        this.user.setUdpPort(udpPort);
        this.user.setConnectionId(id);
        this.user.setName("player"+ existingUsers.size());

        existingUsers.put(id, this.user);
        existingUsers.get(user.getConnectionId()).setThread(this);
    }

    //TODO: lepsze nadawanie id pokoi
    private void newRoom(){
        Room room = new Room(rooms.size(), this.user);
        room.addUserToRoom(this.user);

        Gdx.app.postRunnable(() -> TcpServer.createGameController(room));

        sendMessage(new JSONObject()
                .put("msg", "createdRoom")
                .put("content", new JSONObject().put("name", user.getName()).put("id", room.getId())));
    }

    private void joinRoom(int roomId){
        boolean roomExists = false;
        for(Room room : rooms){
            if(room.getId() == roomId) {
                roomExists = true;
                if(room.checkIfUserCanJoinRoom()) {
                    room.addUserToRoom(user);
                    sendMessage(new JSONObject()
                            .put("msg", "joinedRoom")
                            .put("content", new JSONObject().put("name", user.getName()).put("id", room.getId())));
                }
                else
                    sendMessage(new JSONObject()
                            .put("msg", "fullRoom")
                            .put("content", new JSONObject().put("id", room.getId())));
                break;
            }
        }

        if(!roomExists)
            sendMessage(new JSONObject()
                    .put("msg", "roomDoesNotExist")
                    .put("content", new JSONObject().put("name", user.getName())));
    }

    private void leaveRoom(){
        Room currentRoom = RoomList.getUserRoom(this.user.getConnectionId());
        currentRoom.deleteUserFromRoom(this.user);
    }

    private void startGame(){
        Room currentRoom = RoomList.getUserRoom(this.user.getConnectionId());
        initGame(currentRoom);

        JSONObject content = new JSONObject()
                .put("owner", user.getName())
                .put("opponents", currentRoom.getGameController().getOpponentsData())
                .put("players", currentRoom.getGameController().getPlayersData());

        JSONObject msg = new JSONObject()
                .put("msg", "startGame");


        for(User currentUser : currentRoom.getUsersInRoom()){
            content.put("current",currentUser.getName());
            content.put("playerType", currentUser.getPlayer().getPlayerType());
            msg.put("content", content);
            currentUser.getThread().sendMessage(msg);
        }

        currentRoom.setGameStarted(true);
        currentRoom.getGameController().hasStarted = true;
    }

    //TODO: przejściowa wersja, do ogarnięcia
    private void initGame(Room room){
        //TODO Here server must know what character user is
        String USER_TYPE = "Water";
        for(User user : room.getUsersInRoom()){
            user.initializePlayer(room.getGameController(), USER_TYPE);
        }
        room.getGameController().setPlayersPosition();
        room.getGameController().spawnAllOpponents();
    }
}
