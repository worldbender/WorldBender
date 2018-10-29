package com.my.game.Player;

import com.badlogic.gdx.graphics.Texture;
import java.awt.*;

public abstract class APlayer extends Rectangle {
    public Texture texture;
    protected String name;
    boolean currentPlayer = false;
    public APlayer(){

    }
    public APlayer(Texture t) {
        super();
        this.texture = t;
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
}
