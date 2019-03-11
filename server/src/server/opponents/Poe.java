package server.opponents;

import server.connection.GameController;
import server.opponents.opponentAI.ChaserAI;
import server.opponents.opponentAI.IdlerAI;

public class Poe extends AOpponent{
    public Poe(GameController gameController){
        super(gameController);
        this.setType("Poe");
        this.setWidth(60);
        this.setHeight(75);
        this.setHp(100);
        this.setBulletType("SpectralTear");
        this.opponentAI = new IdlerAI(this, gameController, true);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        this.opponentAI.behave(deltaTime);
    }
    @Override
    protected void handleOpponentDeath(){
        super.handleOpponentDeath();
        this.dropRandomPickup();
    }
}
