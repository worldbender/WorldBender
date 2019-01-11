package RoomsController;

import server.User;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private int id;
    private int limitUsers = 4;
    private String name;
    private CopyOnWriteArrayList<User> usersInRoom;

    public Room(){
        setId();
    }

    public CopyOnWriteArrayList<User> getUsersInRoom(){
        return this.usersInRoom;
    }

    public void addUserToRoom(){

    }

    public void deleteUserFromRoom(){

    }

    public int getId(){
        return this.id;
    }

    private void setId(){

    }
}
