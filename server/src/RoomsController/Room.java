package RoomsController;

import server.User;
import server.connection.GameController;

import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private int id;
    private int limitUsers = 4;
    private String name;
    private CopyOnWriteArrayList<User> usersInRoom;
    private Thread senderThread;
    private GameController sender;

    public Room(int id){
        setId(id);
        usersInRoom = new CopyOnWriteArrayList<>();
        sender = new GameController(this);
        senderThread = new Thread(sender);
        senderThread.start();
    }

    public CopyOnWriteArrayList<User> getUsersInRoom(){
        return this.usersInRoom;
    }

    public void addUserToRoom(User user){
        this.usersInRoom.add(user);
    }

    public void deleteUserFromRoom(User userToDelete){
        for(User user : usersInRoom){
            if(user.getName() == userToDelete.getName())
                usersInRoom.remove(user);
        }
    }

    public int getId(){
        return this.id;
    }

    private void setId(int id){
        this.id = id;
    }
}
