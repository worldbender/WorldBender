package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
import server.opponents.opponentAI.IdlerAI;
import server.pickups.PickupList;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Nietzsche extends AOpponent {
    public Nietzsche(GameController gameController) {
        super(gameController);
        this.setType("Nietzsche");
        this.setWidth(200);
        this.setHeight(250);
        this.setHp(500);
        this.setViewRange(800.0);
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
        this.dropRandomPickup();
    }
}

