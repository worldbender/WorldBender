package com.my.game.Opponent;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

/**
 * Created by lucyna on 02.11.18.
 */
public abstract class AOpponent extends Rectangle {
    public String name = "Monster";
    private int xPos = 0;
    private int yPos = 0;
    private int direction = 0;
    private boolean alive = false;
    private int health = 0;
    private int attack = 0;
    private int movement = 0;
    public Texture texture;

    public AOpponent() {
    }

    public AOpponent(Texture t) {
        super();
        this.texture = t;
    }

    public int getxPos() {
        return this.xPos;
    }

    public int getyPos() {
        return this.yPos;
    }

    public int getDirection() {
        return this.direction;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public int getHealth() {
        return this.health;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

}
