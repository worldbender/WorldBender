package server.opponents;

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

    public void update(double deltaTime){
        Random generator = new Random();
        this.setX(this.getX() + generator.nextInt()%10);
        this.setY(this.getY() + generator.nextInt()%10);
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
