package com.my.game.player;

import com.badlogic.gdx.graphics.Texture;
import java.awt.*;

public abstract class APlayer extends Rectangle {
    protected String name;
    protected boolean currentPlayer = false;
    private int hp;
    public APlayer(){

    }

    public void setPosition(int x, int y){
        this.setX(x);
        this.setY(y);
    }

    @Override
    public double getX() {
        return (int)super.getX();
    }

    @Override
    public double getY() {
        return (int)super.getY();
    }
    public void setX(int x){
        super.x = x;
    }
    public void setY(int y){
        super.y = y;
    }
    public void setX(double x){
        super.x = (int)x;
    }
    public void setY(double y){
        super.y = (int)y;
    }

    public boolean isCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
