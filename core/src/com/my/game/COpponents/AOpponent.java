package com.my.game.COpponents;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by lucyna on 02.11.18.
 */
public abstract class AOpponent{
    public String name = "Monster";
    private int x = 0;
    private int y = 0;
    private int direction = 0;
    private boolean alive = false;
    private int health = 0;
    private int attack = 0;
    private int movement = 0;
    private int id;
    private Texture texture;


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
