package com.my.game.COpponents;

public class OpponentFabric {
    public static AOpponent createOpponent(String opponentType, int id, float angle){
        AOpponent resultOpponent = null;
        switch (opponentType){
            case "Schopenheuer":
                resultOpponent = new Schopenheuer();
                break;
        }
        return resultOpponent;
    }
}
