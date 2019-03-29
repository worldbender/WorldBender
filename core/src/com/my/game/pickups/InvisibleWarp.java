package com.my.game.pickups;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class InvisibleWarp extends APickup {
    public static Animation<TextureRegion> openDoorsAnimation;
    public InvisibleWarp(int x, int y, int id) {
        super(x, y, id, "InvisibleWarp");
    }
    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(openDoorsAnimation.getKeyFrames()[0], this.getX(), this.getY());
    }
}
