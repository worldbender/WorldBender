package server.bullets;

import server.LogicMap.LogicMapHandler;
import server.Player;
import server.User;
import server.opponents.AOpponent;
import server.opponents.OpponentList;

import java.awt.*;
import java.util.Map;

public class ABullet {
    private int x;
    private int y;
    private float angle;
    private int id;
    private int range;
    private int width = 5;
    private int height = 5;
    private String type;
    private double bulletSpeed = 1;
    private boolean isDead = false;
    private int attack = 1;
    private boolean hostile;

    protected ABullet(int x, int y, float angle, boolean hostile){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.hostile = hostile;
    }
    public void update(double deltaTime, LogicMapHandler map, Map<String, User> existingUsers){
        int newX = this.getX() + (int)(deltaTime * Math.cos(angle) * bulletSpeed);
        int newY = this.getY() + (int)(deltaTime * Math.sin(angle) * bulletSpeed);
        Rectangle bounds = new Rectangle(newX, newY, this.width, this.height);
        handleAllBulletCollisions(deltaTime, map, existingUsers, bounds);
        this.checkIfBulletShouldDie();
    }

    private void handleAllBulletCollisions(double deltaTime, LogicMapHandler map, Map<String, User> existingUsers, Rectangle bounds){
        this.handleBulletCollisionWithMap(map, bounds);
        this.handleBulletCollisionWithOpponents(bounds);
        this.handleBulletCollisionWithPlayers(bounds, existingUsers);
        this.handleBulletShift(bounds.x, bounds.y, deltaTime);
    }

    private void handleBulletCollisionWithMap(LogicMapHandler map, Rectangle bounds){
        if(map.isRectangleCollidesWithMap(bounds)){
            this.isDead = true;
        }
    }

    private void handleBulletCollisionWithOpponents(Rectangle rec){
        for(AOpponent opponent : OpponentList.getOpponents()){
            if(rec.intersects(opponent.getBounds()) && !this.isHostile()){
                opponent.doDamage(this.attack);
                this.isDead = true;
            }
        }
    }

    private void handleBulletCollisionWithPlayers(Rectangle rec, Map<String, User> existingUsers){
        Player player;
        for(User user : existingUsers.values()){
            player = user.getPlayer();
            if(rec.intersects(player.getBounds()) && this.isHostile()){
                player.doDamage(this.attack);
                this.isDead = true;
            }
        }
    }

    private void handleBulletShift(int newX, int newY, double deltaTime){
        this.setX(newX);
        this.setY(newY);
        decrementRange(deltaTime);
    }

    private void checkIfBulletShouldDie(){
        if(this.isDead){
            deleteBullet();
        }
    }

    private void decrementRange(double deltaTime){
        double shift;
        if(this.getRange() > 0){
            shift = Math.sqrt((
                    deltaTime * Math.cos(angle) * bulletSpeed) * (deltaTime * Math.cos(angle) * bulletSpeed) +
                    (deltaTime * Math.sin(angle) * bulletSpeed) *(deltaTime * Math.sin(angle) * bulletSpeed)
            );
            this.setRange(this.getRange() - (int)shift);
        } else{
            this.isDead = true;
        }
    }

    private void deleteBullet(){
        BulletList.removeBullet(this);
        BulletList.addDeadBulletsTrashList(this);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }
}
