package com.my.game.opponents;

public class OpponentFabric {
    public static AOpponent createOpponent(String opponentType, int id){
        AOpponent resultOpponent = null;
        switch (opponentType){
            case "Schopenheuer":
                resultOpponent = new Schopenheuer(id);
                break;
            case "Nietzsche":
                resultOpponent = new Nietzsche(id);
                break;
        }
        return resultOpponent;
    }
}
