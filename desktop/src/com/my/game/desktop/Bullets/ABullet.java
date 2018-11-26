package com.my.game.desktop.Bullets;

public class ABullet {
    private int x;
    private int y;
    private float angle;
    private int id;
    private int range;
    private String type;
    private double bulletSpeed = 1;
    protected ABullet(int x, int y, float angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    public void update(double deltaTime){
        this.setX(this.getX());
        this.setY(this.getY() + 5);
        if(this.getRange() > 0){
            this.setRange(this.getRange() - 5);
        } else{
            BulletList.removeBullet(this);
        }
        //TODO Add real logic that bases on angle
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
