package com.my.game.desktop.Blocks;

import com.my.game.desktop.Blocks.ABlock;

public class SolidBlock extends ABlock {
    public SolidBlock(int x1, int y1, int width, int height) {
        super(x1, y1, width, height);
        this.blockType = "Solid";
    }

}
