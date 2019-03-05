package server.opponents;

import server.connection.GameController;
import server.pickups.PickupFabric;
import java.awt.*;
import java.util.Random;

public class Schopenheuer extends AOpponent{
    public Schopenheuer(GameController gameController){
        super(gameController);
        this.setType("Schopenheuer");
        this.setWidth(288);
        this.setHeight(286);
        this.setHp(500);
        this.setViewRange(1000.0);
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
        this.pickupList.addPickup(PickupFabric.createPickup(this.getCenterX(), this.getCenterY(),"Warp"));
    }
}
