package server.bullets;

import server.connection.GameController;

public class BulletFactory {
    private BulletFactory(){}
    public static ABullet createBullet(String bulletType, int x, int y, float angle, boolean hostile, GameController gameController){
        ABullet resultBullet = null;
        switch (bulletType){
            case "Tear":
                resultBullet = new Tear(x, y, angle, hostile, gameController);
                break;
            case "SpectralTear":
                resultBullet = new SpectralTear(x, y, angle, hostile, gameController);
                break;
            case "FireRing":
                resultBullet = new FireRing(x, y, angle, hostile, gameController);
                break;
            default:
                throw new IllegalArgumentException("There is no such type of a bullet!");
        }
        return resultBullet;
    }
}
