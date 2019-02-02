package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.bullets.BulletFabric;
import server.bullets.BulletList;
import server.pickups.PickupList;

import java.awt.*;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Nietzsche extends AOpponent {
    public Nietzsche() {
        super();
        this.setType("Nietzsche");
        this.setWidth(200);
        this.setHeight(250);
        this.setHp(500);
        this.setViewRange(800.0);
    }

    @Override
    public void update(double deltaTime, LogicMapHandler map, CopyOnWriteArrayList<User> usersInRoom, BulletList bulletList, OpponentList opponentList, PickupList pickupList) {
        super.update(deltaTime, map, usersInRoom, bulletList, opponentList, pickupList);
        Random generator = new Random();
        int newX = (int)this.getX() + generator.nextInt()%3;
        int newY = (int)this.getY() + generator.nextInt()%3;
        Rectangle newPosRectangle = new Rectangle(newX, newY, this.getWidth(), this.getHeight());
        if(!isOpponentCollidesWithMap(newPosRectangle, map)){
            this.setX(newX);
            this.setY(newY);
        }
        this.handleOpponentShoot(usersInRoom, bulletList);
    }
}

