package server.bullets;

import server.LogicMap.LogicMapHandler;
import server.players.Player;
import server.User;
import server.connection.GameController;
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
    protected OpponentList opponentList;
    protected BulletList bulletList;
    protected CopyOnWriteArrayList<User> usersInRoom;
    protected LogicMapHandler map;
    protected GameController gameController;

    protected ABullet(int x, int y, double angle, boolean hostile, GameController gameController){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.hostile = hostile;
        this.opponentList = gameController.opponentList;
        this.bulletList = gameController.bulletList;
        this.gameController = gameController;
        this.map = gameController.logicMapHandler;
    }
    public void update(double deltaTime, CopyOnWriteArrayList<User> usersInRoom){
        double newX = this.getX() + (deltaTime * Math.cos(angle) * bulletSpeed);
        double newY = this.getY() + (deltaTime * Math.sin(angle) * bulletSpeed);
        Rectangle bounds = new Rectangle((int)newX, (int)newY, this.width, this.height);
        handleAllBulletCollisions(deltaTime, usersInRoom, bounds, newX, newY);
        this.checkIfBulletShouldDie();
    }

    protected void handleAllBulletCollisions(double deltaTime, CopyOnWriteArrayList<User> usersInRoom, Rectangle bounds, double newX, double newY){
        this.handleBulletCollisionWithMap(bounds);
        this.handleBulletCollisionWithOpponents(bounds);
        this.handleBulletCollisionWithPlayers(bounds, usersInRoom);
        this.handleBulletShift(newX, newY, deltaTime);
    }

    protected void handleBulletCollisionWithMap( Rectangle bounds){
        if(this.map.isRectangleCollidesWithMap(bounds)){
            this.isDead = true;
        }
    }

    protected void handleBulletCollisionWithOpponents(Rectangle rec){
        for(AOpponent opponent : this.opponentList.getOpponents()){
            if(rec.intersects(opponent.getBounds()) && !this.isHostile()){
                opponent.doDamage(this.attack);
                this.isDead = true;
            }
        }
    }

    protected void handleBulletCollisionWithPlayers(Rectangle rec, CopyOnWriteArrayList<User> usersInRoom){
        Player player;
        for(User user : usersInRoom){
            player = user.getPlayer();
            if(rec.intersects(player.getBounds()) && this.isHostile()){
                player.doDamage(this.attack);
                this.isDead = true;
            }
        }
    }

    protected void handleBulletShift(double newX, double newY, double deltaTime){
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
        this.bulletList.deleteBullet(this);
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
