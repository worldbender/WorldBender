package server.rooms;

import com.badlogic.gdx.Gdx;
import org.json.JSONObject;
import server.User;
import server.connection.TcpClientThread;
import server.connection.TcpServer;

import java.util.List;

public class RoomController {
    private TcpClientThread clientThread;
    private List<Room> rooms;

    public RoomController(TcpClientThread clientThread){
        this.clientThread = clientThread;
        this.rooms = RoomList.getInstance();
    }

    //TODO: lepsze nadawanie id pokoi
    public void newRoom(User user){
        Room room = new Room(rooms.size(), user);
        room.addUserToRoom(user);

        Gdx.app.postRunnable(() -> TcpServer.createGameController(room));

        clientThread.sendMessage(new JSONObject()
                .put("msg", "createdRoom")
                .put("content", new JSONObject().put("name", user.getName()).put("id", room.getId())));
    }

    public void joinRoom(User user, int roomId){
        boolean roomExists = false;
        for(Room room : rooms){
            if(room.getId() == roomId) {
                roomExists = true;
                if(room.checkIfUserCanJoinRoom()) {
                    room.addUserToRoom(user);
                    clientThread.sendMessage(new JSONObject()
                            .put("msg", "joinedRoom")
                            .put("content", new JSONObject().put("name", user.getName()).put("id", room.getId())));
                }
                else
                    clientThread.sendMessage(new JSONObject()
                            .put("msg", "fullRoom")
                            .put("content", new JSONObject().put("id", room.getId())));
                break;
            }
        }

        if(!roomExists)
            clientThread.sendMessage(new JSONObject()
                    .put("msg", "roomDoesNotExist")
                    .put("content", new JSONObject().put("name", user.getName())));
    }

    public void leaveRoom(User user){
        Room currentRoom = RoomList.getUserRoom(user.getConnectionId());
        currentRoom.deleteUserFromRoom(user);
    }

    public void startGame(User user){
        Room currentRoom = RoomList.getUserRoom(user.getConnectionId());
        initGame(currentRoom);

        JSONObject content = new JSONObject().put("owner", user.getName()).put("opponents", currentRoom.getGameController().getOpponentsData()).put("players", currentRoom.getGameController().getPlayersData());

        JSONObject msg = new JSONObject()
                .put("msg", "startGame");


        for(User currentUser : currentRoom.getUsersInRoom()){
            content.put("current",currentUser.getName());
            msg.put("content", content);
            currentUser.getThread().sendMessage(msg);
        }

        currentRoom.setGameStarted(true);
    }

    //TODO: przejściowa wersja, do ogarnięcia
    public void initGame(Room room){
        for(User user : room.getUsersInRoom()){
            user.initializePlayer(room.getGameController());
        }
        room.getGameController().setPlayersPosition();
        room.getGameController().spawnAllOpponents();
    }

}
