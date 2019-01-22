package server.opponents;

public class OpponentFabric {
    public static AOpponent createOpponent(String opponentType){
        AOpponent resultOpponent = null;
        switch (opponentType){
            case "Schopenheuer":
                resultOpponent = new Schopenheuer();
                break;
            case "Nietzsche":
                resultOpponent = new Nietzsche();
                break;
            case "Poe":
                resultOpponent = new Poe();
                break;
        }
        return resultOpponent;
    }
}