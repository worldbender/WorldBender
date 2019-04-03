package com.my.game.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FireRing extends ABullet {
    public static Animation<TextureRegion> fireRingAnimation;
    public FireRing(int id, float angle){
        this.setId(id);
        this.setX(-50);
        this.setY(-50);
        this.setAngle(angle);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float stateTime) {
        this.drawBullet(spriteBatch, fireRingAnimation.getKeyFrame(stateTime, true));
    }
}
