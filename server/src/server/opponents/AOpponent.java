package server.opponents;

import server.LogicMap.LogicMapHandler;

import java.awt.*;
import java.util.Date;
import java.util.Random;

public abstract class AOpponent {
    private int x = 300;
    private int y = 300;
    private int width;
    private int height;
    private int id;
    private String type;
    private int hp;
    private long shootCooldown = 100L;
    private long lastTimePlayerHasShot = 0L;
    private boolean isDead = false;

    protected AOpponent(){
    }

    public void update(double deltaTime, LogicMapHandler map){
        Random generator = new Random();
        int newX = this.getX() + generator.nextInt()%5;
        int newY = this.getY() + generator.nextInt()%5;
        Rectangle newPosRectangle = new Rectangle(newX, newY, this.getWidth(), this.getHeight());
        if(!isOpponentCollidesWithMap(newPosRectangle, map)){
            this.setX(newX);
            this.setY(newY);
        }
    }

    public boolean isOpponentCollidesWithMap(Rectangle rec, LogicMapHandler map){
        return map.isRectangleCollidesWithMap(rec);
    }

    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void doDamage(int damage){
        this.hp -= damage;
        if(this.hp <= 0){
            this.handleOpponentDeath();
        }
    }
    public boolean canOpponentShoot(){
        boolean result = false;
        Date date= new Date();
        long time = date.getTime();
        if(time - this.lastTimePlayerHasShot > this.shootCooldown){
            result = true;
            this.lastTimePlayerHasShot = time;
        }
        return result;
    }
    private void handleOpponentDeath(){
        OpponentList.removeOpponent(this);
        OpponentList.addDeadAOpponentsTrashList(this);
    }
    private void checkIfOpponentShouldDie(){
        if(this.isDead){
            handleOpponentDeath();
        }
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getCenterX(){
        return this.getX() + (int)(this.getWidth()/2.0);
    }

    public int getCenterY(){
        return this.getY() + (int)(this.getHeight()/2.0);
    }
}
