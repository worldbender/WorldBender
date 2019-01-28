package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.bullets.BulletFabric;
import server.bullets.BulletList;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Nietzsche extends AOpponent {
    public Nietzsche() {
        super();
        this.setType("Nietzsche");
        this.setWidth(200);
        this.setHeight(250);
        this.setHp(1000);
        this.setViewRange(800.0);
    }

    @Override
    public void update(double deltaTime, LogicMapHandler map, CopyOnWriteArrayList<User> usersInRoom, BulletList bulletList, OpponentList opponentList) {
        double sqare;
        super.update(deltaTime, map, usersInRoom, bulletList, opponentList);
        if (this.canOpponentShoot()) {
            for (User user : usersInRoom) {
                sqare = Math.sqrt((Math.abs(user.getPlayer().getCenterY() - this.getCenterY())) * (Math.abs(user.getPlayer().getCenterY() - this.getCenterY())) +
                        (Math.abs(this.getCenterX() - user.getPlayer().getCenterX()) * (Math.abs(this.getCenterX() - user.getPlayer().getCenterX()))));
                if (sqare < this.getViewRange()) {
                    float angle = (float) (Math.atan2(user.getPlayer().getCenterY() - this.getCenterY(), this.getCenterX() - user.getPlayer().getCenterX()));
                    ABullet newBullet = BulletFabric.createBullet("Tear", this.getCenterX(), this.getCenterY(), -angle + (float) Math.PI, true);
                    bulletList.addBullet(newBullet);
                    bulletList.addBulletsToCreateList(newBullet);
                }
            }
        }
    }
}

