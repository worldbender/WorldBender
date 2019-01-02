package com.my.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.UtilitySingletons.ShapeDrawer;
import com.my.game.UtilitySingletons.StringDrawer;


public class Player extends APlayer {
    public static double maxHp = 10;
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
        this.drawHp(batch);
        this.drawName(batch);
    }

    private void drawHp(SpriteBatch batch){
        batch.end();
        ShapeDrawer.drawHp(batch, this.texture.getHeight(), (int)this.getX(), (int)this.getY(), this.getHp(), (int)Player.maxHp);
        batch.begin();
    }

    private void drawName(SpriteBatch batch){
        StringDrawer.drawHp(batch, this.getName(), (int)this.getX(), (int)this.getY() + this.texture.getHeight());
    }

    public boolean isCurrentPlayer(){
        return currentPlayer;
    }

    public String getName(){
        return name;
    }

}
