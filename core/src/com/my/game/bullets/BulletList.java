package com.my.game.bullets;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletList {
    private static CopyOnWriteArrayList<ABullet> bullets=new CopyOnWriteArrayList<>();
    public static int id = 0;
    private BulletList(){

    }
    public static List<ABullet> getBullets(){
        return bullets;
    }
    public static void addBullet(ABullet bullet){
        bullets.add(bullet);
    }
    public static void removeBullet(ABullet bullet){
        bullets.remove(bullet);
    }
    public static void setBulletPosition(int id, int x, int y){
        for(ABullet bullet : bullets){
            if(bullet.getId() == id){
                bullet.setX(x);
                bullet.setY(y);
            }
        }
    }
    public static void removeBulletById(int id){
        for(ABullet bullet : bullets){
            if(bullet.getId() == id){
                bullets.remove(bullet);
            }
        }
    }
}
