package server.bullets;

public class Tear extends ABullet {
    public Tear(int x, int y, float angle, boolean hostile) {
        super(x, y, angle, hostile);
        this.setRange(800);
        this.setType("Tear");
        this.setAttack(10);
    }
}
