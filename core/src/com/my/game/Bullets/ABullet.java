package com.my.game.Bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ABullet {
    private int x;
    private int y;
    private int width;
    private int height;
    private int id;
    private float angle;
    private Texture texture;
    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(this.texture, x, y);
    }
    public void setPosition(int x, int y){
        this.setX(x);
        this.setY(y);
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public Texture getTexture(){
        return this.texture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
