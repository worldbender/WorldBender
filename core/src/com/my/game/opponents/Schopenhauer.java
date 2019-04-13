package com.my.game.opponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

import static com.my.game.MyAssetManager.SCHOPEN;

public class Schopenhauer extends AOpponent {
    private static final Texture TEXTURE = MyAssetManager.manager.get(SCHOPEN);
    public Schopenhauer(int id){
        super(id);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(Schopenhauer.TEXTURE, this.getX(), this.getY());
        this.drawHp(spriteBatch, Schopenhauer.TEXTURE.getHeight());
        this.drawName(spriteBatch, "Schopenhauer", Schopenhauer.TEXTURE.getHeight());
    }
}
