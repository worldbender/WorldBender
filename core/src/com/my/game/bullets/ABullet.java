package com.my.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class ABullet {
    private int x;
    private int y;
    private int width;
    private int height;
    private int id;
    private float angle;

    public abstract void draw(SpriteBatch spriteBatch, float stateTime);

    protected void drawBullet(SpriteBatch spriteBatch, Texture texture) {
        drawBullet(spriteBatch, new TextureRegion(texture));
    }

    protected void drawBullet(SpriteBatch spriteBatch, TextureRegion textureRegion) {
        float angle = ((this.getAngle() - (float) (Math.PI / 2)) * (float) (180f / Math.PI));
        spriteBatch.draw(
                textureRegion,
                x,
                y,
                textureRegion.getRegionWidth() / 2f,
                textureRegion.getRegionHeight() / 2f,
                textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(),
                1f,
                1f,
                angle
        );

    }

    public void setPosition(int x, int y) {
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
