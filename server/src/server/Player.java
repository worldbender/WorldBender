package server;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class Player {

    private int x=500;
    private int y=500;
    private int width;
    private int height;
    private int hp=10;
    private int moveSpeed = 1;
    private long shootCooldown = 100L;
    private long lastTimePlayerHasShot = 0L;
    private long lastTimePlayerHasChangedMovment = 0L;
    private long changeMovementCooldown = 20L;
    private String activeMovementKey = "DOWN";
    private boolean isMoving = false;
    public static final int PLAYER_TEXTURE_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_WIDTH"));
    public static final int PLAYER_TEXTURE_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_HEIGHT"));
    private float scale = 2f;

    public Player(){
        this.setWidth((int)(PLAYER_TEXTURE_WIDTH * scale));
        this.setHeight((int)(PLAYER_TEXTURE_HEIGHT * scale));
    }

    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, this.getWidth(), this.getHeight());
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

    public boolean canPlayerShoot(){
        boolean result = false;
        Date date= new Date();
        long time = date.getTime();
        if(time - this.lastTimePlayerHasShot > this.shootCooldown){
            result = true;
            this.lastTimePlayerHasShot = time;
        }
        return result;
    }

    public boolean canPlayerChangeMovement(){
        boolean result = false;
        Date date= new Date();
        long time = date.getTime();
        if(time - this.lastTimePlayerHasChangedMovment > this.changeMovementCooldown){
            result = true;
            this.lastTimePlayerHasChangedMovment = time;
        }
        return result;
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
    public boolean isPlayersCollidesWithAnything(Rectangle rec, LogicMapHandler map, ArrayList<Player> players){
        return isPlayerCollidesWithMap(rec, map) ||
                isRectangleCollidesWithPlayers(rec, players);
    }

    public void setActiveMovementKeyByAngle(String angle){
        float parsedAngle = Float.parseFloat(angle);
        if(Math.abs(parsedAngle - Math.PI) < 0.001f){
            setActiveMovementKey("LEFT");
        } else if(Math.abs(parsedAngle - Math.PI/2) < 0.001f){
            setActiveMovementKey("UP");
        } else if(Math.abs(parsedAngle) < 0.001f){
            setActiveMovementKey("RIGHT");
        } else {
            setActiveMovementKey("DOWN");
        }
    }

    public void setPosition(String content, LogicMapHandler map, Map<String, User> users){
        char letter = content.charAt(0);
        Rectangle playersNewBoundsRectangle;
        ArrayList<Player> players = new ArrayList<Player>();
        double deltaTime = 5.0;
        int currentShift = (int)(deltaTime * this.moveSpeed);

        for(User user : users.values()){
            if(user.getPlayer() != this){
                players.add(user.getPlayer());
            }
        }

        if(letter=='A'){
            this.setActiveMovementKey("LEFT");
            this.setMoving(true);
            playersNewBoundsRectangle = new Rectangle(this.x - currentShift, this.y, this.getWidth(), this.getHeight());
            if(!isPlayersCollidesWithAnything(playersNewBoundsRectangle, map, players)){
                this.x -= currentShift;
            }
        }
        else if(letter=='D'){
            this.setActiveMovementKey("RIGHT");
            this.setMoving(true);
            playersNewBoundsRectangle = new Rectangle(this.x + currentShift, this.y, this.getWidth(), this.getHeight());
            if(!isPlayersCollidesWithAnything(playersNewBoundsRectangle, map, players)){
                this.x += currentShift;
            }
        }
        else if(letter=='W'){
            this.setActiveMovementKey("UP");
            this.setMoving(true);
            playersNewBoundsRectangle = new Rectangle(this.x, this.y + currentShift, this.getWidth(), this.getHeight());
            if(!isPlayersCollidesWithAnything(playersNewBoundsRectangle, map, players)){
                this.y += currentShift;
            }
        }
        else if(letter=='S'){
            this.setActiveMovementKey("DOWN");
            this.setMoving(true);
            playersNewBoundsRectangle = new Rectangle(this.x, this.y - currentShift, this.getWidth(), this.getHeight());
            if(!isPlayersCollidesWithAnything(playersNewBoundsRectangle, map, players)){
                this.y -= currentShift;
            }
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setShootCooldown(long shootCooldown) {
        this.shootCooldown = shootCooldown;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public String getActiveMovementKey() {
        return activeMovementKey;
    }

    public void setActiveMovementKey(String activeMovementKey) {
        this.activeMovementKey = activeMovementKey;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public int getCenterX(){
        return this.getX() + (int)(this.getWidth()/2.0);
    }

    public int getCenterY(){
        return this.getY() + (int)(this.getHeight()/2.0);
    }
}
