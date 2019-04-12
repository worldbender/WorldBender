package server.logicmap;

public class SolidBlock extends ABlock {
    public SolidBlock(int x1, int y1, int width, int height) {
        super(x1, y1, width, height);
        this.blockType = "Solid";
    }
    public SolidBlock(int x1, int y1, int width, int height, boolean isDoor) {
        super(x1, y1, width, height);
        this.blockType = "Solid";
        this.setDoor(isDoor);
    }
}
