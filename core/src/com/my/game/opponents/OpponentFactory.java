package com.my.game.opponents;

public class OpponentFactory {
    public static AOpponent createOpponent(String opponentType, int id){
        AOpponent resultOpponent = null;
        switch (opponentType){
            case "Schopenhauer":
                resultOpponent = new Schopenhauer(id);
                break;
            case "Nietzsche":
                resultOpponent = new Nietzsche(id);
                break;
            case "Poe":
                resultOpponent = new Poe(id);
                break;
        }
        return resultOpponent;
    }
}
