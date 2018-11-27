package com.my.game.CBullets;

public class BulletFabric {
    public static ABullet createBullet(String bulletType, int id, float angle){
        ABullet resultBullet = null;
        switch (bulletType){
            case "Tear":
                resultBullet = new Tear(id, angle);
                break;
        }
        return resultBullet;
    }
}
