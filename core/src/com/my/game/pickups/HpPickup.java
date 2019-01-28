package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HpPickup extends APickup {
    public static Texture texture;
    public HpPickup(int x, int y, int id){
        super(x, y, id, "Hp");
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(HpPickup.texture, this.getX(), this.getY());
    }
}
