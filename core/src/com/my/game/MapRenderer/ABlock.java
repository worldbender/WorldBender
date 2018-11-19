package com.my.game.MapRenderer;

import java.awt.*;

public abstract class ABlock {
    protected Rectangle rectangle;
    protected String blockType;
    private ABlock(){

    }
    public ABlock(int x1, int y1, int x2, int y2){
        rectangle = new Rectangle(x1,y2,x2,y2);
    }
    public ABlock(Point p1, Point p2){
        rectangle = new Rectangle(p1.x, p1.y, p2.x, p2.y);
    }

    public String getType(){
        return this.blockType;
    }
    public void setType(String type){
        this.blockType = type;
    }
}
