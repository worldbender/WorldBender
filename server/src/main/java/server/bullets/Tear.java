package server.bullets;

import server.connection.GameController;

public class Tear extends ABullet {
    public Tear(int x, int y, float angle, boolean hostile, GameController gameController) {
        super(x, y, angle, hostile, gameController);
        this.setRange(800);
        this.setType("Tear");
        this.setAttack(10);
    }
}
