package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

public class SadOnion extends APickup{
    private static final Texture TEXTURE = MyAssetManager.manager.get(MyAssetManager.SAD_ONION);
    public SadOnion(int x, int y, int id){
        super(x, y, id, "SadOnion");
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(SadOnion.TEXTURE, this.getX(), this.getY());
    }
}
