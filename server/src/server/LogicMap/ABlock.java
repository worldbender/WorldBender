package server.LogicMap;

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
     * @param width
     * @param height
     * x1 and y1 are position of UPPER LEFT corner, width and height are width and height
     */
    public ABlock(int x1, int y1, int width, int height){
        rectangle = new Rectangle(x1,y1,width,height);
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
