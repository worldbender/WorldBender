package server.pickups;

import server.players.Player;
import java.awt.*;

public abstract class APickup{
    private String type;
    private double x;
    private double y;
    private int id;
    private final int WIDTH = 64;
    private final int HEIGHT = 64;
    private boolean isBlocked = false;
    private String direction = "up";
    public APickup(){
    }

    public abstract void modifyPlayer(Player player);

    public Rectangle getBounds(){
        return new Rectangle((int)this.getX(), (int)this.getY(), this.WIDTH, this.HEIGHT);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
