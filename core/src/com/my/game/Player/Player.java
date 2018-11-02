package com.my.game.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
<<<<<<< HEAD:core/src/com/my/game/Player.java
import com.badlogic.gdx.math.Rectangle;
//hej haj hello
public class Player extends Rectangle{
    public Texture texture;
    public String name;
    boolean currentPlayer = false;
=======

>>>>>>> ba79ff86d823b64219ec83eb79317e650c275b87:core/src/com/my/game/Player/Player.java

public class Player extends APlayer {

    public Player(Texture texture, boolean currentPlayer){
        this.texture = texture;
        this.setSize(texture.getWidth(), texture.getHeight());
        this.currentPlayer = currentPlayer;
    }
    public Player(String name, String x, String y){
        this.name = name;
        this.setPosition(Integer.parseInt(x), Integer.parseInt(y));
    }

    public Player(String name, int x, int y){
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
