package server.rooms;

import server.ExistingUsers;
import server.User;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomList {
    private static int nextRoomId = 1;
    private static List<Room> rooms;
    private static Map<String, User> existingUsers = ExistingUsers.getInstance();

    private RoomList(){ }

    public static final List<Room> getInstance(){
        if(rooms == null){
            rooms = new CopyOnWriteArrayList<>();
        }
        return rooms;
    }

    public static Room getRoom(int id){
        for(Room room : rooms){
            if (room.getId() == id)
                return room;
        }
        return null;
    }

    public static Room getUserRoom(String id){
        User currentUser = existingUsers.get(id);
        for(Room room : rooms){
            for(User user : room.getUsersInRoom()){
                if(user.getConnectionId().equals(currentUser.getConnectionId())) return room;
            }
        }
        return null;
    }

    public static int getNextRoomId() {
        return nextRoomId++;
    }

}
