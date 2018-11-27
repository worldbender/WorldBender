package com.my.game.desktop.SBullets;

import com.my.game.desktop.LogicMapHandler;
import com.my.game.desktop.SOpponents.AOpponent;
import com.my.game.desktop.SOpponents.OpponentList;

import java.awt.*;

public class ABullet {
    private int x;
    private int y;
    private float angle;
    private int id;
    private int range;
    private int width = 5;
    private int height = 5;
    private String type;
    private double bulletSpeed = 1;
    private boolean isDead = false;
    private int attack = 1;
    protected ABullet(int x, int y, float angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }
    public void update(double deltaTime, LogicMapHandler map){
        int newX = this.getX() + (int)(deltaTime * Math.cos(angle) * bulletSpeed);
        int newY = this.getY() + (int)(deltaTime * Math.sin(angle) * bulletSpeed);
        Rectangle bounds = new Rectangle(this.x, this.y, this.width, this.height);
        this.handleBulletCollisionWithOpponent(bounds);
        if(!map.isRectangleCollidesWithMap(bounds)){
            this.handleBulletShift(newX, newY, deltaTime);
        } else{
            this.isDead = true;
        }
        this.checkIfBulletShoudDie();
    }

    private void handleBulletCollisionWithOpponent(Rectangle rec){
        for(AOpponent opponent : OpponentList.getOpponents()){
            if(rec.intersects(opponent.getBounds())){
                opponent.doDamage(this.attack);
                this.isDead = true;
            }
        }
    }

    private void handleBulletShift(int newX, int newY, double deltaTime){
        this.setX(newX);
        this.setY(newY);
        decrementRange(deltaTime);
    }

    private void checkIfBulletShoudDie(){
        if(this.isDead){
            deleteBullet();
        }
    }

    private void decrementRange(double deltaTime){
        double shift;
        if(this.getRange() > 0){
            shift = Math.sqrt((
                    deltaTime * Math.cos(angle) * bulletSpeed) * (deltaTime * Math.cos(angle) * bulletSpeed) +
                    (deltaTime * Math.sin(angle) * bulletSpeed) *(deltaTime * Math.sin(angle) * bulletSpeed)
            );
            this.setRange(this.getRange() - (int)shift);
        } else{
            this.deleteBullet();
        }
    }

    private void deleteBullet(){
        BulletList.removeBullet(this);
        BulletList.addDeadBulletsTrashList(this);
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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
