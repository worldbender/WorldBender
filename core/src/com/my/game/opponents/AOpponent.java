package com.my.game.opponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.UtilitySingletons.ShapeDrawer;
import com.my.game.UtilitySingletons.StringDrawer;
import com.my.game.music.MusicPlayer;
import com.my.game.player.Player;

/**
 * Created by lucyna on 02.11.18.
 */
public abstract class AOpponent{
    public String name = "Monster";
    private int x = 0;
    private int y = 0;
    private int direction = 0;
    private boolean alive = false;
    private int hp = 500;
    private int maxHp = 1000;
    private int attack = 0;
    private int movement = 0;
    private int id;

    protected AOpponent(int id){
        this.id = id;
    }
    public abstract void draw(SpriteBatch spriteBatch);

    public void die(){
//        MusicPlayer.initMusic("sounds/meow.mp3");
//        MusicPlayer.playStaticMusic();
    }

    protected void drawHp(SpriteBatch batch, int textureHeight){
        batch.end();
        ShapeDrawer.drawHp(batch, textureHeight, this.getX(), this.getY(), this.getHp(), this.getMaxHp());
        batch.begin();
    }
    protected void drawName(SpriteBatch batch, String name, int textureHeight){
        StringDrawer.drawHp(batch, name, this.getX(), this.getY() + textureHeight);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}
