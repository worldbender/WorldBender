package com.my.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player extends APlayer {

    public Player(Texture texture, boolean currentPlayer){
        super();
        this.texture = texture;
        this.setSize(texture.getWidth(), texture.getHeight());
        this.currentPlayer = currentPlayer;
    }
    public Player(String name, String x, String y){
        super();
        this.name = name;
        this.setPosition(Integer.parseInt(x), Integer.parseInt(y));
    }

    public Player(String name, int x, int y){
        super();
        this.name = name;
        this.setPosition(x, y);
    }

    public Player(){

    }
    public void draw(SpriteBatch batch){
        batch.draw(this.texture, (int)this.getX(), (int)this.getY());
    }

    public boolean isCurrentPlayer(){
        return currentPlayer;
    }

    public String getName(){
        return name;
    }

}
