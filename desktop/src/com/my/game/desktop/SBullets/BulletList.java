package com.my.game.desktop.SBullets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BulletList {
    private static List<ABullet> bullets = Collections.synchronizedList(new ArrayList<ABullet>());
    private static List<ABullet> deadBullets = Collections.synchronizedList(new ArrayList<ABullet>());
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
