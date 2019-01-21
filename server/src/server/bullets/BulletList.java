package server.bullets;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletList {
    private CopyOnWriteArrayList<ABullet> bulletsToCreate = new CopyOnWriteArrayList<ABullet>();
    private CopyOnWriteArrayList<ABullet> bullets = new CopyOnWriteArrayList<ABullet>();
    private CopyOnWriteArrayList<ABullet> deadBullets = new CopyOnWriteArrayList<ABullet>();

    public static int id = 0;
    public BulletList(){

    }
    public void addBullet(ABullet bullet){
        bullet.setId(id);
        id++;
        bullets.add(bullet);
    }

    //BulletList
    public List<ABullet> getBullets(){
        return bullets;
    }
    public void removeBullet(ABullet bullet){
        bullets.remove(bullet);
    }

    //DeadBulletList
    public List<ABullet> getDeadBullets(){
        return deadBullets;
    }
    public void addDeadBulletsTrashList(ABullet bullet){
        deadBullets.add(bullet);
    }
    public void flushDeadBullets(){
        deadBullets.clear();
    }

    //BulletsToCreateList
    public CopyOnWriteArrayList<ABullet> getBulletsToCreate() {
        return bulletsToCreate;
    }
    public void addBulletsToCreateList(ABullet bullet){
        bulletsToCreate.add(bullet);
    }
    public void flushBulletsToCreate(){
        bulletsToCreate.clear();
    }


}
