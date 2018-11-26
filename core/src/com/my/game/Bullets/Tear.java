package com.my.game.Bullets;

import com.badlogic.gdx.graphics.Texture;

public class Tear extends ABullet{
    public Tear(int id, float angle){
        this.setId(id);
        this.setX(100);
        this.setY(100);
        this.setAngle(angle);
    }
}
