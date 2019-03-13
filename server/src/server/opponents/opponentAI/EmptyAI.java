package server.opponents.opponentAI;

import server.connection.GameController;
import server.opponents.AOpponent;

public class EmptyAI extends AOpponentAI {
    public EmptyAI(AOpponent opponent, GameController gameController, boolean isChangeable) {
        super(opponent, gameController, isChangeable);
    }

    @Override
    public void behave(double deltaTime) {
        if(this.shouldChangeAI()){
            this.changeAI();
        }
    }

    @Override
    public boolean shouldChangeAI() {
        return false;
    }

    @Override
    public void changeAI() {

    }
}
