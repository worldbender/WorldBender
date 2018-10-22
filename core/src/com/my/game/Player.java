package com.my.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Rectangle{
    public Texture texture;
    public String name;
    boolean currentPlayer = false;

    public boolean isCurrentPlayer(){
        return currentPlayer;
    }

    public Player(Texture texture, boolean currentPlayer){
        this.texture = texture;
        this.height = texture.getHeight();
        this.width = texture.getWidth();
        this.currentPlayer = currentPlayer;
    }
    public Player(String name, String x, String y){
        this.name = name.substring(1, 8);
        this.x=Integer.parseInt(x);
        this.y=Integer.parseInt(y);
    }

    public Player(String name, int x, int y){
        this.name = name;
        this.x=x;
        this.y=y;
    }

    public Player(){

    }

    public void draw(SpriteBatch batch){
        batch.draw(this.texture, x, y);
    }

    public String getName(){
        return name;
    }

}
