package server;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Player {

    private int x=500;
    private int y=500;
    private int hp=10;
    private int width = 56;
    private int height = 56;
    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    public boolean isPlayerCollidesWithMap(Rectangle rec, LogicMapHandler map){
        return map.isRectangleCollidesWithMap(rec);
    }

    public boolean isRectangleCollidesWithPlayers(Rectangle rec, ArrayList<Player> players){
        boolean result = false;
        for(Player player : players){
            if(rec.intersects(player.getBounds())){
                result = true;
            }
        }
        return result;
    }
    public boolean isPlayersCollidesWithEnything(Rectangle rec, LogicMapHandler map, ArrayList<Player> players){
        return isPlayerCollidesWithMap(rec, map) ||
                isRectangleCollidesWithPlayers(rec, players);
    }
    public void setPosition(String content, LogicMapHandler map, Map<String, User> users){
        char letter = content.charAt(0);
        Rectangle playersNewBoundsRectangle;
        ArrayList<Player> players = new ArrayList<Player>();

        for(User user : users.values()){
            if(user.getPlayer() != this){
                players.add(user.getPlayer());
            }
        }
        if(letter=='A'){
            playersNewBoundsRectangle = new Rectangle(this.x - 5, this.y, this.width, this.height);
            if(!isPlayersCollidesWithEnything(playersNewBoundsRectangle, map, players)){
                this.x-=5;
            }
        }
        else if(letter=='D'){
            playersNewBoundsRectangle = new Rectangle(this.x + 5, this.y, this.width, this.height);
            if(!isPlayersCollidesWithEnything(playersNewBoundsRectangle, map, players)){
                this.x+=5;
            }
        }
        else if(letter=='W'){
            playersNewBoundsRectangle = new Rectangle(this.x, this.y + 5, this.width, this.height);
            if(!isPlayersCollidesWithEnything(playersNewBoundsRectangle, map, players)){
                this.y+=5;
            }
        }
        else if(letter=='S'){
            playersNewBoundsRectangle = new Rectangle(this.x, this.y - 5, this.width, this.height);
            if(!isPlayersCollidesWithEnything(playersNewBoundsRectangle, map, players)){
                this.y-=5;
            }
        }
    }
}
