package com.my.game.COpponents;

import java.util.concurrent.CopyOnWriteArrayList;

public class OpponentList {
    private static CopyOnWriteArrayList<AOpponent> opponents=new CopyOnWriteArrayList<AOpponent>();
    private OpponentList(){

    }
    public void addOpponent(AOpponent opponent){

    }
    public void removeOpponent(AOpponent opponent){
        opponents.remove(opponent);
    }
    public static void setBulletPosition(int id, int x, int y){
        for(AOpponent opponent : opponents){
            if(opponent.getId() == id){
                opponent.setX(x);
                opponent.setY(y);
            }
        }
    }
}
