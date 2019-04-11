package server.opponents;

import server.connection.GameController;

public class OpponentFactory {
    public static AOpponent createOpponent(String opponentType, GameController gameController){
        AOpponent resultOpponent = null;
        switch (opponentType){
            case "Schopenhauer":
                resultOpponent = new Schopenhauer(gameController);
                break;
            case "Nietzsche":
                resultOpponent = new Nietzsche(gameController);
                break;
            case "Poe":
                resultOpponent = new Poe(gameController);
                break;
        }
        return resultOpponent;
    }
}