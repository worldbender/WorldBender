package com.my.game.opponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

import static com.my.game.MyAssetManager.NIETZSCHE;

public class Nietzsche extends AOpponent {
    private static final Texture TEXTURE = MyAssetManager.manager.get(NIETZSCHE);
    public Nietzsche(int id){
        super(id);
        this.setHp(500);
        this.setMaxHp(500);
    }
    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(Nietzsche.TEXTURE, this.getX(), this.getY());
        this.drawHp(spriteBatch, Nietzsche.TEXTURE.getHeight());
        this.drawName(spriteBatch, "Nietzsche", Nietzsche.TEXTURE.getHeight());
    }

}
