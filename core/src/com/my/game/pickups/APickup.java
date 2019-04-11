package com.my.game.pickups;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class APickup {
    private int x;
    private int y;
    private String type;
    private int id;
    protected APickup(int x, int y, int id, String type){
        this.x = x;
        this.y = y;
        this.id = id;
        this.type = type;
    }
    public abstract void draw(SpriteBatch spriteBatch, float stateTime);

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
