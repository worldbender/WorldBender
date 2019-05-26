package com.my.game.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ground extends Player {
    public static Animation<TextureRegion> heads;
    public static TextureRegion headRegion;
    public Ground(String name, String id) {
        super(name, id);
    }
    public Ground(String name, int x, int y, String id) {
        super(name, x, y, id);
    }

    @Override
    protected Animation<TextureRegion> getHeads() {
        return heads;
    }

    public TextureRegion getHeadRegion(){
        return headRegion;
    }
}
