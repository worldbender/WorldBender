package com.my.game.utilitySingletons;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StringDrawer {
    private static BitmapFont font = new BitmapFont();
    private static void drawString(SpriteBatch batch, String string, int x, int y){
        font.draw(batch, string, x, y);
    }
    public static void drawHp(SpriteBatch batch, String string, int x, int y){
        drawString(batch, string, x, y + (int)font.getCapHeight());
    }
}
