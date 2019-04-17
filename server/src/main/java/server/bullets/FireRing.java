package server.bullets;

import server.connection.GameController;

public class FireRing extends ABullet{
    public FireRing(int x, int y, float angle, boolean hostile, GameController gameController) {
        super(x, y, angle, hostile, gameController);
        this.setRange(800);
        this.setType("FireRing");
        this.setAttack(10);
        this.width = 64;
        this.height = 64;
    }
}
