package com.my.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpectralTear extends ABullet {
    public static Texture spectralTearTexture;
    public SpectralTear(int id, float angle){
        this.setId(id);
        this.setX(-50);
        this.setY(-50);
        this.setAngle(angle);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        this.drawBullet(spriteBatch, spectralTearTexture);
    }
}
