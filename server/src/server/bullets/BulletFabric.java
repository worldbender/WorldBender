package server.bullets;

import server.connection.GameController;

public class BulletFabric {
    public static ABullet createBullet(String bulletType, int x, int y, float angle, boolean hostile, GameController gameController){
        ABullet resultBullet = null;
        switch (bulletType){
            case "Tear":
                resultBullet = new Tear(x, y, angle, hostile, gameController);
                break;
        }
        return resultBullet;
    }
}
