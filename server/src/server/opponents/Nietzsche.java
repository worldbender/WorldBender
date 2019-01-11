package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.bullets.BulletFabric;
import server.bullets.BulletList;

public class Nietzsche extends AOpponent {
    public Nietzsche(){
        super();
        this.setType("Nietzsche");
        this.setWidth(200);
        this.setHeight(250);
        this.setHp(1000);
    }

    @Override
    public void update(double deltaTime, LogicMapHandler map) {
        super.update(deltaTime, map);
        if(this.canOpponentShoot()){
            BulletList.addBullet(BulletFabric.createBullet("Tear", this.getCenterX(), this.getCenterY(), 0f, true));
        }
    }

}
