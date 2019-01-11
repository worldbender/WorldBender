package server.bullets;

public class BulletFabric {
    public static ABullet createBullet(String bulletType, int x, int y, float angle, boolean hostile){
        ABullet resultBullet = null;
        switch (bulletType){
            case "Tear":
                resultBullet = new Tear(x, y, angle, hostile);
                break;
        }
        return resultBullet;
    }
}
