package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomList {
    private static List<Room> rooms;

    private RoomList(){ }

    public static final List getInstance(){
        if(rooms == null){
            rooms = Collections.synchronizedList(new ArrayList<Room>());
        }
        return rooms;
    }
}
