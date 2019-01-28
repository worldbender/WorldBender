package server.opponents;

import server.LogicMap.LogicMapHandler;
import server.Player;
import server.User;
import server.bullets.ABullet;
import server.bullets.BulletFabric;
import server.bullets.BulletList;
import server.pickups.PickupFabric;
import server.pickups.PickupList;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AOpponent {
    private double x = 300;
    private double y = 300;
    private double speed = 0.25;
    private double viewRange = 600;
    private int width;
    private int height;
    private int id;
    private String type;
    private int hp;
    private long shootCooldown = 1000L;
    private long lastTimePlayerHasShot = 0L;
    private long chaseCooldown = 2000L;
    private long lastTimeOpponentHasChandedTargetToChase= 0L;
    private boolean isDead = false;
    private String idOfChasedPlayer = "";

    protected AOpponent(){
    }

    public void update(double deltaTime, LogicMapHandler map, CopyOnWriteArrayList<User> usersInRoom, BulletList bulletList, OpponentList opponentList, PickupList pickupList){
        checkIfOpponentShouldDie(opponentList, pickupList);
    }

    protected void handleOpponentShoot(CopyOnWriteArrayList<User> usersInRoom, BulletList bulletList){
        double distance;
        float angle;
        if(this.canOpponentShoot()){
            for (User user : usersInRoom) {
                distance = Math.sqrt((Math.abs(user.getPlayer().getCenterY() - this.getCenterY())) * (Math.abs(user.getPlayer().getCenterY() - this.getCenterY())) +
                        (Math.abs(this.getCenterX() - user.getPlayer().getCenterX()) * (Math.abs(this.getCenterX() - user.getPlayer().getCenterX()))));
                if (distance < this.getViewRange()) {
                    angle = (float) (Math.atan2(user.getPlayer().getCenterY() - this.getCenterY(), this.getCenterX() - user.getPlayer().getCenterX()));
                    ABullet newBullet = BulletFabric.createBullet("Tear", this.getCenterX(), this.getCenterY(), -angle + (float) Math.PI, true);
                    bulletList.addBullet(newBullet);
                    bulletList.addBulletsToCreateList(newBullet);
                }
            }
        }
    }

    protected void choosePlayerToChaseIfTimeComes(CopyOnWriteArrayList<User> usersInRoom){
        double distance;
        if(this.shouldOpponentChangeChaseTarget()){
            this.setIdOfChasedPlayer("");
            double savedDistance = Float.POSITIVE_INFINITY;
            for (User user : usersInRoom) {
                distance = Math.sqrt((Math.abs(user.getPlayer().getCenterY() - this.getCenterY())) * (Math.abs(user.getPlayer().getCenterY() - this.getCenterY())) +
                        (Math.abs(this.getCenterX() - user.getPlayer().getCenterX()) * (Math.abs(this.getCenterX() - user.getPlayer().getCenterX()))));
                if (distance < this.getViewRange() && distance < savedDistance) {
                    this.setIdOfChasedPlayer(user.getName());
                    savedDistance = distance;
                }
            }
        }
    }

    protected void chasePlayer(CopyOnWriteArrayList<User> usersInRoom, double deltaTime, LogicMapHandler map, OpponentList opponentList){
        float angle;
        double newX;
        double newY;
        for (User user : usersInRoom) {
            if(user.getName().equals(this.getIdOfChasedPlayer())){
                angle = (float) (Math.atan2(user.getPlayer().getCenterY() - this.getCenterY(), this.getCenterX() - user.getPlayer().getCenterX()));
                newX = this.getX() + (deltaTime * Math.cos(-angle + (float) Math.PI) * this.getSpeed());
                newY = this.getY() + (deltaTime * Math.sin(-angle + (float) Math.PI) * this.getSpeed());
                Rectangle newPosRectangle = new Rectangle((int)newX, (int)newY, this.getWidth(), this.getHeight());
                if(!this.isOpponentCollidesWithMap(newPosRectangle, map) && !this.isOpponentCollidesWithOpponents(newPosRectangle, opponentList)){
                    this.setX(newX);
                    this.setY(newY);
                }
            }
        }
    }

    public boolean isOpponentCollidesWithOpponents(Rectangle rectangle, OpponentList opponentList){
        boolean result = false;

        for(AOpponent opponent : opponentList.getOpponents()){
            if(opponent != this){
                if(opponent.getBounds().intersects(rectangle)){
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean isOpponentCollidesWithMap(Rectangle rec, LogicMapHandler map){
        return map.isRectangleCollidesWithMap(rec);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)this.x, (int)this.y, this.width, this.height);
    }

    public void doDamage(int damage, OpponentList opponentList){
        this.hp -= damage;
    }
    public boolean canOpponentShoot(){
        boolean result = false;
        Date date= new Date();
        long time = date.getTime();
        if(time - this.lastTimePlayerHasShot > this.shootCooldown){
            result = true;
            this.lastTimePlayerHasShot = time;
        }
        return result;
    }
    public boolean shouldOpponentChangeChaseTarget(){
        boolean result = false;
        Date date= new Date();
        long time = date.getTime();
        if(time - this.lastTimeOpponentHasChandedTargetToChase > this.chaseCooldown){
            result = true;
            this.lastTimeOpponentHasChandedTargetToChase = time;
        }
        return result;
    }

    private void handleOpponentDeath(OpponentList opponentList, PickupList pickupList){
        opponentList.removeOpponent(this);
        opponentList.addDeadAOpponentsTrashList(this);
        pickupList.addPickup(PickupFabric.createPickup(this.getCenterX(), this.getCenterY(),"Hp"));
    }

    private void checkIfOpponentShouldDie(OpponentList opponentList, PickupList pickupList){
        if(this.getHp() <= 0){
            this.isDead = true;
        }
        if(this.isDead){
            handleOpponentDeath(opponentList, pickupList);
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getCenterX(){
        return (int)this.getX() + (int)(this.getWidth()/2.0);
    }

    public int getCenterY(){
        return (int)this.getY() + (int)(this.getHeight()/2.0);
    }

    public double getViewRange() {
        return viewRange;
    }

    public void setViewRange(double viewRange) {
        this.viewRange = viewRange;
    }

    public long getChaseCooldown() {
        return chaseCooldown;
    }

    public void setChaseCooldown(long chaseCooldown) {
        this.chaseCooldown = chaseCooldown;
    }

    public long getLastTimeOpponentHasChandedTargetToChase() {
        return lastTimeOpponentHasChandedTargetToChase;
    }

    public void setLastTimeOpponentHasChandedTargetToChase(long lastTimeOpponentHasChandedTargetToChase) {
        this.lastTimeOpponentHasChandedTargetToChase = lastTimeOpponentHasChandedTargetToChase;
    }

    public String getIdOfChasedPlayer() {
        return idOfChasedPlayer;
    }

    public void setIdOfChasedPlayer(String idOfChasedPlayer) {
        this.idOfChasedPlayer = idOfChasedPlayer;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
