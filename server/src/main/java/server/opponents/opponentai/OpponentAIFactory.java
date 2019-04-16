package server.opponents.opponentai;

import server.connection.GameController;
import server.opponents.AOpponent;

public class OpponentAIFactory {
    public static IOpponentAI createOpponentAI(String type, AOpponent opponent, GameController gameController, boolean isChangable){
        IOpponentAI resultOpponentAI;
        switch (type){
            case "Chaser":
                resultOpponentAI = new ChaserAI(opponent, gameController, isChangable);
                break;
            case "Idler":
                resultOpponentAI = new IdlerAI(opponent, gameController, isChangable);
                break;
            default:
                resultOpponentAI = new EmptyAI(opponent, gameController, isChangable);
                break;
        }
        return resultOpponentAI;
    }
}
