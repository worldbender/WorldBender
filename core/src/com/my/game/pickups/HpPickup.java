package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

import static com.my.game.MyAssetManager.HP;

public class HpPickup extends APickup {
    private static final Texture TEXTURE = MyAssetManager.manager.get(HP);
    public HpPickup(int x, int y, int id){
        super(x, y, id, "Hp");
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(HpPickup.TEXTURE, this.getX(), this.getY());
    }
}
