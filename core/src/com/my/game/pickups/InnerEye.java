package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

public class InnerEye extends APickup{
    public static Texture texture = MyAssetManager.manager.get(MyAssetManager.InnerEye);
    public InnerEye(int x, int y, int id){
        super(x, y, id, "InnerEye");
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(InnerEye.texture, this.getX(), this.getY());
    }
}
