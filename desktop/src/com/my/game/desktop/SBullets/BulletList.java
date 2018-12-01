package com.my.game.desktop.SBullets;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BulletList {
    private static CopyOnWriteArrayList<ABullet> bullets = new CopyOnWriteArrayList<ABullet>();
    private static CopyOnWriteArrayList<ABullet> deadBullets = new CopyOnWriteArrayList<ABullet>();
    public static int id = 0;
    private BulletList(){

    }
    public static List<ABullet> getBullets(){
        return bullets;
    }
    public static List<ABullet> getDeadBullets(){
        return deadBullets;
    }
    public static void addBullet(ABullet bullet){
        bullet.setId(id);
        id++;
        bullets.add(bullet);
    }
    public static void removeBullet(ABullet bullet){
        bullets.remove(bullet);
    }
    public static void addDeadBulletsTrashList(ABullet bullet){
        deadBullets.add(bullet);
    }
    public static void deleteBullet(ABullet bullet){
        deadBullets.remove(bullet);
    }
    public static void flushDeadBullets(){
        deadBullets.clear();
    }
}
