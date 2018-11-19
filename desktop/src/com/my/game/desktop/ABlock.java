package com.my.game.desktop;

import java.awt.*;

public abstract class ABlock {
    protected Rectangle rectangle;
    protected String blockType;
    private ABlock(){

    }

    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * x1 and y1 are position of UPPER LEFT corner, x2 and y2 are width and height
     */
    public ABlock(int x1, int y1, int x2, int y2){

        rectangle = new Rectangle(x1,y1,x2,y2);
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
    public Rectangle getRectangle(){
        return this.rectangle;
    }
}
