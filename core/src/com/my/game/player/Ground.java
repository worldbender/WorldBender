package com.my.game.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ground extends Player {
    public static Animation<TextureRegion> heads;
    public Ground(String name) {
        super(name);
    }
    public Ground(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    protected Animation<TextureRegion> getHeads() {
        return heads;
    }
}
