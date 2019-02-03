package server.bullets;

import server.LogicMap.LogicMapHandler;
import server.Player;
import server.User;
import server.opponents.AOpponent;
import server.opponents.OpponentList;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ABullet {
    private double x;
    private double y;
    private double angle;
    private int id;
    private int range;
    private int width = 15;
    private int height = 28;
    private String type;
    private double bulletSpeed = 1;
    private boolean isDead = false;
    private int attack = 1;
    private boolean hostile;

    protected ABullet(int x, int y, double angle, boolean hostile){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.hostile = hostile;
    }
    public void update(double deltaTime, LogicMapHandler map, CopyOnWriteArrayList<User> usersInRoom, OpponentList opponentList, BulletList bulletList){
        double newX = this.getX() + (deltaTime * Math.cos(angle) * bulletSpeed);
        double newY = this.getY() + (deltaTime * Math.sin(angle) * bulletSpeed);
        Rectangle bounds = new Rectangle((int)newX, (int)newY, this.width, this.height);
        handleAllBulletCollisions(deltaTime, map, usersInRoom, bounds, newX, newY, opponentList);
        this.checkIfBulletShouldDie(bulletList);
    }

    private void handleAllBulletCollisions(double deltaTime, LogicMapHandler map, CopyOnWriteArrayList<User> usersInRoom, Rectangle bounds, double newX, double newY, OpponentList opponentList){
        this.handleBulletCollisionWithMap(map, bounds);
        this.handleBulletCollisionWithOpponents(bounds, opponentList);
        this.handleBulletCollisionWithPlayers(bounds, usersInRoom);
        this.handleBulletShift(newX, newY, deltaTime);
    }

    private void handleBulletCollisionWithMap(LogicMapHandler map, Rectangle bounds){
        if(map.isRectangleCollidesWithMap(bounds)){
            this.isDead = true;
        }
    }

    private void handleBulletCollisionWithOpponents(Rectangle rec, OpponentList opponentList){
        for(AOpponent opponent : opponentList.getOpponents()){
            if(rec.intersects(opponent.getBounds()) && !this.isHostile()){
                opponent.doDamage(this.attack, opponentList);
                this.isDead = true;
            }
        }
    }

    private void handleBulletCollisionWithPlayers(Rectangle rec, CopyOnWriteArrayList<User> usersInRoom){
        Player player;
        for(User user : usersInRoom){
            player = user.getPlayer();
            if(rec.intersects(player.getBounds()) && this.isHostile()){
                player.doDamage(this.attack);
                this.isDead = true;
            }
        }
    }

    private void handleBulletShift(double newX, double newY, double deltaTime){
        this.setX(newX);
        this.setY(newY);
        decrementRange(deltaTime);
    }

    private void checkIfBulletShouldDie(BulletList bulletList){
        if(this.isDead){
            deleteBullet(bulletList);
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

    private void deleteBullet(BulletList bulletList){
        bulletList.deleteBullet(this);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
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
