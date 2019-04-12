package server.LogicMap;

import java.awt.*;

public abstract class ABlock {
    protected Rectangle rectangle;
    protected String blockType;
    private boolean isDoor = false;
    private boolean isPortal = false;
    private String portalDirection = "up";
    protected ABlock(){

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


    public String getBLockType(){
        return this.blockType;
    }
    public void setBlockType(String type){
        this.blockType = type;
    }
    public Rectangle getRectangle(){
        return this.rectangle;
    }

    public boolean isDoor() {
        return isDoor;
    }

    public void setDoor(boolean door) {
        isDoor = door;
    }

    public boolean isPortal() {
        return isPortal;
    }

    public void setPortal(boolean portal) {
        isPortal = portal;
    }

    public String getPortalDirection() {
        return portalDirection;
    }

    public void setPortalDirection(String portalDirection) {
        this.portalDirection = portalDirection;
    }
}
