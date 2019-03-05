package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.BulletList;
import server.connection.GameController;
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
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        Random generator = new Random();
        int newX = (int)this.getX() + generator.nextInt()%3;
        int newY = (int)this.getY() + generator.nextInt()%3;
        Rectangle newPosRectangle = new Rectangle(newX, newY, this.getWidth(), this.getHeight());
        if(!isOpponentCollidesWithMap(newPosRectangle)){
            this.setX(newX);
            this.setY(newY);
        }
        this.handleOpponentShoot();
    }

    @Override
    protected void handleOpponentDeath(){
        super.handleOpponentDeath();
        this.dropRandomPickup();
    }
}

