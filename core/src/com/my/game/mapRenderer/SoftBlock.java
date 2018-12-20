package com.my.game.mapRenderer;

import java.awt.*;

public class SoftBlock extends ABlock {
    public SoftBlock(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        this.blockType = "Soft";
    }

    public SoftBlock(Point p1, Point p2) {
        super(p1, p2);
    }
}
