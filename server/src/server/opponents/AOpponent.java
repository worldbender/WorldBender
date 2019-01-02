package server.opponents;

import server.LogicMapHandler;

import java.awt.*;
import java.util.Random;

public class AOpponent {
    private int x = 300;
    private int y = 300;
    private int width;
    private int height;
    private int id;
    private String type;
    private int hp;


    protected AOpponent(){
    }

    public void update(double deltaTime, LogicMapHandler map){
        Random generator = new Random();
        int newX = this.getX() + generator.nextInt()%10;
        int newY = this.getY() + generator.nextInt()%10;
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
    private void handleOpponentDeath(){
        OpponentList.addToDeadOpponents(this);
        OpponentList.removeOpponent(this);
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
}
