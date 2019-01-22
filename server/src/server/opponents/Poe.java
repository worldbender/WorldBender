package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.bullets.BulletFabric;
import server.bullets.BulletList;
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
    public void update(double deltaTime, LogicMapHandler map, CopyOnWriteArrayList<User> usersInRoom, BulletList bulletList) {
        super.update(deltaTime, map, usersInRoom, bulletList);
        if(this.canOpponentShoot()){
            for(User user : usersInRoom){
                float angle = (float)(Math.atan2(user.getPlayer().getCenterY() - this.getCenterY(), this.getCenterX() - user.getPlayer().getCenterX()));
                ABullet newBullet = BulletFabric.createBullet("Tear", this.getCenterX(), this.getCenterY(), -angle + (float)Math.PI, true);
                bulletList.addBullet(newBullet);
                bulletList.addBulletsToCreateList(newBullet);
            }
        }
    }
}
