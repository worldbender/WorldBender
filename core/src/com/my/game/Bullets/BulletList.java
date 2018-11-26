package com.my.game.Bullets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletList {
    private static CopyOnWriteArrayList<ABullet> bullets=new CopyOnWriteArrayList<ABullet>();
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
}
