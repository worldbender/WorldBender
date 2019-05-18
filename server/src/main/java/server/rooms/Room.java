package server.rooms;

import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
import server.opponents.OpponentList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private int id;
    private int limitOfPlayers = 4;
    private String name;
    private List<Room> rooms;
    private CopyOnWriteArrayList<User> usersInRoom;
    private OpponentList opponentList;
    private BulletList bulletList;
    private GameController gameController;
    private User roomOwner;
    private boolean gameStarted = false;

    public Room(int id, User roomOwner){
        setId(id);
        this.rooms = RoomList.getInstance();
        this.usersInRoom = new CopyOnWriteArrayList<>();
        this.opponentList = new OpponentList();
        this.bulletList = new BulletList(this.usersInRoom);
        setRoomOwner(roomOwner);

        addRoomToList();
    }

    public CopyOnWriteArrayList<User> getUsersInRoom(){
        return this.usersInRoom;
    }

    public boolean canUserJoinRoom(){
        return (this.getUsersInRoom().size() < limitOfPlayers && !this.gameStarted);
    }

    public void addUserToRoom(User user){
        this.usersInRoom.add(user);
    }

    public void deleteUserFromRoom(User userToDelete){
        for (User user : this.usersInRoom) {
            if (user.getConnectionId().equals(userToDelete.getConnectionId()))
                this.usersInRoom.remove(user);
        }
    }

    public int getId(){
        return this.id;
    }

    private void setId(int id){
        this.id = id;
    }

    public OpponentList getOpponentList(){
        return this.opponentList;
    }

    public BulletList getBulletList(){
        return this.bulletList;
    }

    public GameController getGameController(){
        return this.gameController;
    }

    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }

    public User getRoomOwner() {
        return roomOwner;
    }

    private void setRoomOwner(User roomOwner) {
        this.roomOwner = roomOwner;
    }

    public boolean isUserAnOwner(User user){
        return user.getConnectionId().equals(getRoomOwner().getConnectionId());
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    private void addRoomToList(){
        rooms.add(this);
    }

    public void deleteRoom(){
        for(User user : usersInRoom){
            this.usersInRoom.remove(user);
        }

        for(Room room : rooms){
            if(room.getId() == this.getId()) {
                room.getGameController().stopThread();
                rooms.remove(room);
            }
        }
    }

}
