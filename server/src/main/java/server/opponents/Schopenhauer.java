package server.opponents;

import server.connection.GameController;
import server.opponents.opponentai.IdlerAI;
import server.pickups.PickupFactory;

public class Schopenhauer extends AOpponent{
    public Schopenhauer(GameController gameController){
        super(gameController);
        this.setType("Schopenhauer");
        this.setWidth(288);
        this.setHeight(286);
        this.setHp(500);
        this.setViewRange(1000.0);
        this.setBulletType("Tear");
        this.opponentAI = new IdlerAI(this, gameController, false);
    }
    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        this.opponentAI.behave(deltaTime);
        this.handleOpponentShoot();
    }
    @Override
    protected void handleOpponentDeath(){
        super.handleOpponentDeath();
        this.pickupList.addPickup(PickupFactory.createPickup(this.getCenterX(), this.getCenterY(),"Warp"));
    }
}
