package com.my.game.desktop;

import java.awt.*;

public class Player {

    private int x=500;
    private int y=500;
    private int hp=10;

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    public boolean isRectangleCollides(Rectangle rec, LogicMapHandler map){
        boolean result = false;
        int objTileX = ((int)rec.getX()/32) - 1 ;
        objTileX = objTileX < 0 ? 0 : objTileX;
        int objTileY = ((int)rec.getY()/32) - 1;
        objTileY = objTileY < 0 ? 0 : objTileY;
        for(int x = objTileX; x < objTileX + 3; x++){
            for(int y = objTileY; y < objTileY + 3; y++){
                System.out.print(x + " " + y);
                System.out.println(map.getCertainTileByPoint(x * 32, y *32).getType());
                if(map.getCertainTileByPoint(x * 32, y *32).getType() == "Solid"){

                    if(rec.intersects(map.getCertainTileByPoint(x * 32, y * 32).getRectangle())){
                        result = true;
                    }
                }
            }
        }

        return result;
    }

    public void setPosition(String content, LogicMapHandler map){
        char letter = content.charAt(0);
        if(letter=='A'){
            Rectangle rec = new Rectangle(this.x - 5, this.y + 28, 56, 56);
            if(!isRectangleCollides(rec, map)){
                this.x-=5;
            }
        }
        else if(letter=='D'){
            Rectangle rec = new Rectangle(this.x + 5, this.y+ 28, 56, 56);
            if(!isRectangleCollides(rec, map)){
                this.x+=5;
            }
        }
        else if(letter=='W'){
            Rectangle rec = new Rectangle(this.x, this.y + 5+ 28, 56, 56);
            if(!isRectangleCollides(rec, map)){
                this.y+=5;
            }
        }
        else if(letter=='S'){
            Rectangle rec = new Rectangle(this.x, this.y - 5+ 28, 56, 56);
            if(!isRectangleCollides(rec, map)){
                this.y-=5;
            }
        }
    }
}
