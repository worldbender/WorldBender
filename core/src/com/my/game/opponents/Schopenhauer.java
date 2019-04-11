package com.my.game.opponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

import static com.my.game.MyAssetManager.schopen;

public class Schopenhauer extends AOpponent {
    public static Texture texture = MyAssetManager.manager.get(schopen);
    public Schopenhauer(int id){
        super(id);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(Schopenhauer.texture, this.getX(), this.getY());
        this.drawHp(spriteBatch, Schopenhauer.texture.getHeight());
        this.drawName(spriteBatch, "Schopenhauer", Schopenhauer.texture.getHeight());
    }
}
