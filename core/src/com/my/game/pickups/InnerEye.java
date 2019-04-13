package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

public class InnerEye extends APickup{
    private static final Texture TEXTURE = MyAssetManager.manager.get(MyAssetManager.INNER_EYE);
    public InnerEye(int x, int y, int id){
        super(x, y, id, "InnerEye");
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(InnerEye.TEXTURE, this.getX(), this.getY());
    }
}
