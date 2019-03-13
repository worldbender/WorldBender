package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

public class Warp extends APickup{
    public static Texture texture = MyAssetManager.manager.get(MyAssetManager.warp);
    public Warp(int x, int y, int id){
        super(x, y, id, "Warp");
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(Warp.texture, this.getX(), this.getY());
    }
}
