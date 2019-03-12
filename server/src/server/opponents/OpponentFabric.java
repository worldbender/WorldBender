package server.opponents;

import server.connection.GameController;

public class OpponentFabric {
    public static AOpponent createOpponent(String opponentType, GameController gameController){
        AOpponent resultOpponent = null;
        switch (opponentType){
            case "Schopenheuer":
                resultOpponent = new Schopenheuer(gameController);
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