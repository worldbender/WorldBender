package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InnerEye extends APickup{
    public static Texture texture;
    public InnerEye(int x, int y, int id){
        super(x, y, id, "InnerEye");
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(InnerEye.texture, this.getX(), this.getY());
    }
}
