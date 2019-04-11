package com.my.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

import static com.my.game.MyAssetManager.GRANATE;

public class Tear extends ABullet{
    public static Texture tearTexture = MyAssetManager.manager.get(GRANATE);
    public Tear(int id, float angle){
        this.setId(id);
        this.setX(-50);
        this.setY(-50);
        this.setAngle(angle);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        this.drawBullet(spriteBatch, tearTexture);
    }
}
