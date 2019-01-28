package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.bullets.BulletFabric;
import server.bullets.BulletList;
import server.pickups.PickupList;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Poe extends AOpponent{
    public Poe(){
        super();
        this.setType("Poe");
        this.setWidth(60);
        this.setHeight(75);
        this.setHp(100);
    }

    @Override
    public void update(double deltaTime, LogicMapHandler map, CopyOnWriteArrayList<User> usersInRoom, BulletList bulletList, OpponentList opponentList, PickupList pickupList) {
        super.update(deltaTime, map, usersInRoom, bulletList, opponentList, pickupList);
        this.chasePlayer(usersInRoom, deltaTime, map, opponentList);
        this.handleOpponentShoot(usersInRoom, bulletList);
        this.choosePlayerToChaseIfTimeComes(usersInRoom);
    }
}
