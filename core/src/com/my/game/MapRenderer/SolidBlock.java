package com.my.game.MapRenderer;

import java.awt.*;

public class SolidBlock extends ABlock {
    public SolidBlock(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
        this.blockType = "Solid";
    }

    public SolidBlock(Point p1, Point p2) {
        super(p1, p2);
    }
}
