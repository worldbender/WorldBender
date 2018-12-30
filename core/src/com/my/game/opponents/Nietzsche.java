package com.my.game.opponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Nietzsche extends AOpponent {
    public static Texture texture;
    public Nietzsche(int id){
        super(id);
    }
    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(Nietzsche.texture, this.getX(), this.getY());
    }
}
