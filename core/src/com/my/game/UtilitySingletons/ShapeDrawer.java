package com.my.game.utilitysingletons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShapeDrawer {
    private ShapeDrawer(){}
    private static ShapeRenderer shapeRenderer = new ShapeRenderer();
    public static void drawHp(SpriteBatch batch, int textureHeight, int x, int y, int accualHp, int maxHp){
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        if((double)accualHp/maxHp > 0.0){
            shapeRenderer.rect((float)x - 10, y, 10, (float)(textureHeight * ((double)accualHp/maxHp)));
        }
        shapeRenderer.end();
    }
}
