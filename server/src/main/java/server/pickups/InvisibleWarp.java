package server.pickups;

public class InvisibleWarp extends Warp {
    public InvisibleWarp(int x, int y) {
        super(x, y);
        this.setType("InvisibleWarp");
        this.setBlocked(true);
    }
}
