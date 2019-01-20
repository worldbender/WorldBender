package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.bullets.BulletFabric;
import server.bullets.BulletList;

import java.util.Map;

public class Schopenheuer extends AOpponent{
    public Schopenheuer(){
        super();
        this.setType("Schopenheuer");
        this.setWidth(288);
        this.setHeight(286);
        this.setHp(1000);
    }
    @Override
    public void update(double deltaTime, LogicMapHandler map, Map<String, User> existingUsers) {
        super.update(deltaTime, map, existingUsers);
        if(this.canOpponentShoot()){
            for(User user : existingUsers.values()){
                float angle = (float)(Math.atan2(user.getPlayer().getCenterY() - this.getCenterY(), this.getCenterX() - user.getPlayer().getCenterX()));
                ABullet newBullet = BulletFabric.createBullet("Tear", this.getCenterX(), this.getCenterY(), -angle + (float)Math.PI, true);
                BulletList.addBullet(newBullet);
                BulletList.addBulletsToCreateList(newBullet);
            }
        }
    }
}
