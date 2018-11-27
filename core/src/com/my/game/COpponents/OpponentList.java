package com.my.game.COpponents;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OpponentList {
    private static CopyOnWriteArrayList<AOpponent> opponents=new CopyOnWriteArrayList<AOpponent>();
    private OpponentList(){

    }
    public static void addOpponent(AOpponent opponent){
        opponents.add(opponent);
    }
    public static void removeOpponent(AOpponent opponent){
        opponents.remove(opponent);
    }
    public static void setOpponentPosition(int id, int x, int y){
        for(AOpponent opponent : opponents){
            if(opponent.getId() == id){
                opponent.setX(x);
                opponent.setY(y);
            }
        }
    }
    public static List<AOpponent> getOpponents(){
        return opponents;
    }
}