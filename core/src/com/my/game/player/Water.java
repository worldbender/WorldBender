package com.my.game.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Water extends Player {
    public static Animation<TextureRegion> heads;
    public Water(String name) {
        super(name);
    }

    public Water(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    protected Animation<TextureRegion> getHeads() {
        return heads;
    }
}
