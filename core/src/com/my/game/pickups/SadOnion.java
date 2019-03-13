package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

public class SadOnion extends APickup{
    public static Texture texture = MyAssetManager.manager.get(MyAssetManager.SadOnion);
    public SadOnion(int x, int y, int id){
        super(x, y, id, "SadOnion");
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(SadOnion.texture, this.getX(), this.getY());
    }
}
