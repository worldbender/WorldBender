package com.my.game.opponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.MyAssetManager;

import static com.my.game.MyAssetManager.nietzsche;

public class Nietzsche extends AOpponent {
    public static Texture texture = MyAssetManager.manager.get(nietzsche);
    public Nietzsche(int id){
        super(id);
        this.setHp(500);
        this.setMaxHp(500);
    }
    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(Nietzsche.texture, this.getX(), this.getY());
        this.drawHp(spriteBatch, Nietzsche.texture.getHeight());
        this.drawName(spriteBatch, "Nietzsche", Nietzsche.texture.getHeight());
    }

}
