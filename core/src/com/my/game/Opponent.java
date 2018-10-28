package com.my.game;

/**
 * Created by lucyna on 28.10.18.
 */
public class Opponent {

    public String name = "Monster";
    private int xPos = 0;
    private int yPos = 0;
    private int direction = 0;
    private boolean alive=false;
    private int health = 0;
    private int attack = 0;
    private int movement = 0;



    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getHealth() {
        return health;
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
