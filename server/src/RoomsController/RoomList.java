package RoomsController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomList {
    private static List<Room> rooms;

    private RoomList(){ }

    public static final List<Room> getInstance(){
        if(rooms == null){
            rooms = new CopyOnWriteArrayList<>();
        }
        return rooms;
    }

    //TODO: add escape if there is no room with this id
    public static Room getRoom(int id){
        for(Room room : rooms){
            if (room.getId() == id)
                return room;
        }
        return null;
    }

}
