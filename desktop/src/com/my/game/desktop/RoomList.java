package com.my.game.desktop;

import java.util.*;

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
