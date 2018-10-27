package com.my.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image {
    public Texture texture;
    private String name;
    boolean currentPlayer = false;

    public Player(Texture texture, boolean currentPlayer){
        super(texture);
        this.texture = texture;
        this.setSize(texture.getWidth(), texture.getHeight());
        this.currentPlayer = currentPlayer;
    }
    public Player(String name, String x, String y){
        this.name = name.substring(1, 8);
        this.setPosition(Float.parseFloat(x), Float.parseFloat(y));
    }

    public Player(String name, int x, int y){
        this.name = name;
        this.setPosition((float)x, (float)y);
    }

    public Player(){

    }

    public void draw(SpriteBatch batch){
        batch.draw(this.texture, this.getX(), this.getY());
    }

    public boolean isCurrentPlayer(){
        return currentPlayer;
    }

    public String getName(){
        return name;
    }

}
