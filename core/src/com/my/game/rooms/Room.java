package com.my.game.rooms;

public class Room {
    private int playersInRoom;
    private String roomOwner;
    private int roomId;
    private boolean canPlayerJoin = true;
    private String status = "In Lobby";

    public Room(int roomId, String roomOwner, int playersInRoom, boolean gameStatus){
        this.roomId = roomId;
        this.roomOwner = roomOwner;
        this.playersInRoom = playersInRoom;
        if(gameStatus){
            canPlayerJoin = false;
            status = "In Game";
        }
        else if(playersInRoom == 4) {
            canPlayerJoin = false;
            status = "Full Room";
        }
    }

    @Override
    public String toString(){
        String info = String.format("%-30d%30s%30d/4\n", roomId, roomOwner, playersInRoom);
//        return roomId + "\t\t" + roomOwner + "\t\t" + playersInRoom + "/4";
        return info;
    }

    public String getPlayersInRoom() {
        return "" + playersInRoom + "/4";
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public String getRoomId() {
        return "" + roomId;
    }

    public boolean canPlayerJoin() {
        return canPlayerJoin;
    }

    public String getGameStatus(){
        return status;
    }
}
