package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Warp extends APickup{
    public static Texture texture;
    public static Animation<TextureRegion> warpAnimation;
    public Warp(int x, int y, int id){
        super(x, y, id, "Warp");
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(warpAnimation.getKeyFrame(stateTime, true), this.getX(), this.getY());
    }
}
