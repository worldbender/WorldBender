package com.my.game.UtilitySingletons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShapeDrawer {
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    public static void drawHp(SpriteBatch batch, int x, int y, int accualHp, int maxHp){
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x - 10, y, 10, (float)(30 * (accualHp/maxHp)));
        shapeRenderer.end();
    }
}
