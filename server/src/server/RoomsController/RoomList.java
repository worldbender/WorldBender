package server.RoomsController;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomList {
    private List<Room> rooms;

    private RoomList(){
        this.rooms = new CopyOnWriteArrayList<>();
    }

}
