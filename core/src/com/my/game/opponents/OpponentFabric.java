package com.my.game.opponents;

public class OpponentFabric {
    public static AOpponent createOpponent(String opponentType, int id){
        AOpponent resultOpponent = null;
        switch (opponentType){
            case "Schopenheuer":
                resultOpponent = new Schopenheuer(id);
                break;
        }
        return resultOpponent;
    }
}
