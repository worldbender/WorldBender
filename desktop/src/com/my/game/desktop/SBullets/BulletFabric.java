package com.my.game.desktop.SBullets;

public class BulletFabric {
    public static ABullet createBullet(String bulletType, int x, int y, float angle){
        ABullet resultBullet = null;
        switch (bulletType){
            case "Tear":
                resultBullet = new Tear(x, y, angle);
                break;
        }
        return resultBullet;
    }
}
