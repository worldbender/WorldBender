package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
import server.pickups.PickupList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Poe extends AOpponent{
    public Poe(GameController gameController){
        super(gameController);
        this.setType("Poe");
        this.setWidth(60);
        this.setHeight(75);
        this.setHp(100);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        this.chasePlayer(deltaTime);
        this.handleOpponentShoot();
        this.choosePlayerToChaseIfTimeComes();
    }
    @Override
    protected void handleOpponentDeath(){
        super.handleOpponentDeath();
        this.dropRandomPickup();
    }
}
