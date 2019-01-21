package RoomsController;

import com.badlogic.gdx.Gdx;
import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
import server.connection.TcpServer;
import server.opponents.AOpponent;
import server.opponents.OpponentList;

import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private int id;
    private int limitUsers = 4;
    private String name;
    private CopyOnWriteArrayList<User> usersInRoom;
    public OpponentList opponentList;
    public BulletList bulletList;

    public Room(int id){
        setId(id);
        usersInRoom = new CopyOnWriteArrayList<>();
        opponentList = new OpponentList();
        bulletList = new BulletList();
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
