package server.bullets;

public class Tear extends ABullet {
    public Tear(int x, int y, float angle) {
        super(x, y, angle);
        this.setRange(500);
        this.setType("Tear");
        this.setAttack(10);
    }
}
