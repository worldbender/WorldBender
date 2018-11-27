package com.my.game.desktop.SOpponents;

public class OpponentFabric {
    public static AOpponent createOpponent(String opponentType){
        AOpponent resultOpponent = null;
        switch (opponentType){
            case "Schopenheuer":
                resultOpponent = new Schopenheuer();
                break;
        }
        return resultOpponent;
    }
}