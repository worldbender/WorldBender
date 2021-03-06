package com.my.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

public class SpectralTear extends ABullet {
    public static Texture spectralTearTexture = MyAssetManager.manager.get(MyAssetManager.SPECTRAL_TEAR);
    public SpectralTear(int id, float angle){
        this.setId(id);
        this.setX(-50);
        this.setY(-50);
        this.setAngle(angle);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        this.drawBullet(spriteBatch, spectralTearTexture);
    }
}
