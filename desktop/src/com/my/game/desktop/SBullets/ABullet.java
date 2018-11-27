package com.my.game.desktop.SBullets;

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
        this.setX(this.getX() + (int)(deltaTime * Math.cos(angle) * bulletSpeed));
        this.setY(this.getY() + (int)(deltaTime * Math.sin(angle) * bulletSpeed));
        double shift;
        if(this.getRange() > 0){
            shift = Math.sqrt((
                    deltaTime * Math.cos(angle) * bulletSpeed) * (deltaTime * Math.cos(angle) * bulletSpeed) +
                    (deltaTime * Math.sin(angle) * bulletSpeed) *(deltaTime * Math.sin(angle) * bulletSpeed)
            );
            this.setRange(this.getRange() - (int)shift);
        } else{
            BulletList.removeBullet(this);
        }
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
