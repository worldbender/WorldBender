package com.my.game.opponents;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Schopenheuer extends AOpponent {
    public static Texture texture;
    public Schopenheuer(int id){
        super(id);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(Schopenheuer.texture, this.getX(), this.getY());
        this.drawHp(spriteBatch, Schopenheuer.texture.getHeight());
        this.drawName(spriteBatch, "Schopenhauer", Schopenheuer.texture.getHeight());
    }
}
