package com.my.game.desktop;

import java.util.ArrayList;

public class Room {
    private int id;
    private int limitUsers = 4;
    private String name;
    private ArrayList<User> usersInRoom;

    public Room(){
        setId();
    }

    public ArrayList getUsersInRoom(){
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
