package com.my.game.desktop.SOpponents;

import java.util.Random;

public class AOpponent {
    private int x = 200;
    private int y = 200;
    private int id;
    private String type;

    protected AOpponent(){
    }

    public void update(double deltaTime){
        Random generator = new Random();
        this.setX(this.getX() + generator.nextInt()%10);
        this.setY(this.getY() + generator.nextInt()%10);
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
}
