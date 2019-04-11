package com.my.game.pickups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

import static com.my.game.MyAssetManager.HP;

public class HpPickup extends APickup {
    public static Texture texture = MyAssetManager.manager.get(HP);
    public HpPickup(int x, int y, int id){
        super(x, y, id, "Hp");
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        spriteBatch.draw(HpPickup.texture, this.getX(), this.getY());
    }
}
