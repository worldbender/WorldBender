package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SadOnion extends APickup{
    public static Texture texture;
    public SadOnion(int x, int y, int id){
        super(x, y, id, "SadOnion");
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(SadOnion.texture, this.getX(), this.getY());
    }
}
