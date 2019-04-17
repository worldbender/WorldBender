package server.bullets;

import server.User;
import server.connection.GameController;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpectralTear extends ABullet {
    protected SpectralTear(int x, int y, double angle, boolean hostile, GameController gameController) {
        super(x, y, angle, hostile, gameController);
        this.setRange(800);
        this.setType("SpectralTear");
        this.setAttack(10);
    }
    @Override
    protected void handleAllBulletCollisions(double deltaTime, CopyOnWriteArrayList<User> usersInRoom, Rectangle bounds, double newX, double newY){
        this.handleBulletCollisionWithOpponents(bounds);
        this.handleBulletCollisionWithPlayers(bounds, usersInRoom);
        this.handleBulletShift(newX, newY, deltaTime);
    }
}
