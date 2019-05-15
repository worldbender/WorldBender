package server.players;

import org.json.JSONObject;
import server.logicmap.LogicMapHandler;
import server.Properties;
import server.User;
import server.bullets.AttackFactory;
import server.bullets.BulletList;
import server.connection.GameController;
import server.powers.IPower;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Player {

    private double x = 500;
    private double y = 500;
    private int width;
    private int height;
    private int hp = 100;
    public final int MAX_HP = 100;
    private final double MAX_SPEED = 0.5;
    private double moveSpeed = 0.0;
    private double acceleration = 0.0;
    private long shootCooldown = 100L;
    private long lastTimePlayerHasShot = 0L;
    private long shootSpeedModificator = 1L;
    private String activeMovementKey = "DOWN";
    private String headDirection = "DOWN";
    protected String bulletType = "SpectralTear";
    private String weaponType = "Normal";
    private String playerType;
    private ArrayList<String> collectedItems;
    private boolean isMoving = false;
    private int PLAYER_TEXTURE_WIDTH ;
    private int PLAYER_TEXTURE_HEIGHT;
    private float scale;
    private User user;
    protected IPower power;
    public boolean KEY_W = false;
    public boolean KEY_S = false;
    public boolean KEY_A = false;
    public boolean KEY_D = false;
    public boolean UP_ARROW = false;
    public boolean DOWN_ARROW = false;
    public boolean LEFT_ARROW = false;
    public boolean RIGHT_ARROW = false;
    public boolean KEY_SPACE = false;
    private LogicMapHandler map;
    private BulletList bulletList;
    private CopyOnWriteArrayList<User> usersInRoom;
    private GameController gameController;

    public Player(User user) {
        initPlayerTexturePrameters();
        this.setWidth((int) (PLAYER_TEXTURE_WIDTH * scale));
        this.setHeight((int) (PLAYER_TEXTURE_HEIGHT * scale));
        this.user = user;
        this.collectedItems = new ArrayList<>();
    }
    
    
    public Player(User user, GameController gameController){
        this(user);
        this.map = gameController.logicMapHandler;
        this.bulletList = gameController.bulletList;
        this.usersInRoom = gameController.usersInRoom;
        this.gameController = gameController;
        
    }
    
    private void initPlayerTexturePrameters() {
        this.PLAYER_TEXTURE_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_WIDTH"));
        this.PLAYER_TEXTURE_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_HEIGHT"));
        this.scale = Float.parseFloat(Properties.loadConfigFile("PLAYER_SCALE"));
    }
    

    public void update(CopyOnWriteArrayList<User> usersInRoom, double deltaTime) {
        this.handleMovementKeys(usersInRoom, deltaTime);
        if((this.UP_ARROW || this.DOWN_ARROW || this.LEFT_ARROW || this.RIGHT_ARROW) && this.canPlayerShoot()){
            shoot();
        }

        this.power.act(deltaTime);
    }

    //TODO test this method
    private void handleMovementKeys(CopyOnWriteArrayList<User> usersInRoom, double deltaTime){
        double currentShift;
        Rectangle playersNewBoundsRectangle;
        ArrayList<Player> players = new ArrayList<Player>();

        players = usersInRoom.stream()
                .filter(player -> player.getPlayer() != this)
                .map(player -> player.getPlayer())
                .collect(Collectors.toCollection(ArrayList::new));

        if (this.isMoving) {
            currentShift = (deltaTime * this.calculateSpeed(deltaTime));
            if (this.KEY_W) {
                playersNewBoundsRectangle = new Rectangle((int)this.x, (int)(this.y + currentShift), this.getWidth(), this.getHeight());
                if (!isPlayersCollidesWithAnything(playersNewBoundsRectangle, players)) {
                    this.y += currentShift;
                }
            }
            if (this.KEY_S) {
                playersNewBoundsRectangle = new Rectangle((int)this.x, (int)(this.y - currentShift), this.getWidth(), this.getHeight());
                if (!isPlayersCollidesWithAnything(playersNewBoundsRectangle, players)) {
                    this.y -= currentShift;
                }
            }
            if (this.KEY_A) {
                playersNewBoundsRectangle = new Rectangle((int)(this.x - currentShift), (int)this.y, this.getWidth(), this.getHeight());
                if (!isPlayersCollidesWithAnything(playersNewBoundsRectangle, players)) {
                    this.x -= currentShift;
                }
            }
            if (this.KEY_D) {
                playersNewBoundsRectangle = new Rectangle((int)(this.x + currentShift), (int)this.y, this.getWidth(), this.getHeight());
                if (!isPlayersCollidesWithAnything(playersNewBoundsRectangle, players)) {
                    this.x += currentShift;
                }
            }
        }
        else {
            this.moveSpeed = (this.moveSpeed - (0.0003 * deltaTime)) < 0.0 ? 0.0 : this.moveSpeed - (0.0003 * deltaTime);
        }
    }

    public void shoot() {
        float angle = 0f;
        if(this.UP_ARROW){
            angle = (float)Math.PI/2;
        }
        if(this.DOWN_ARROW){
            angle = (float)(3 * Math.PI/2);
        }
        if(this.LEFT_ARROW){
            angle = (float)Math.PI;
        }
        if(this.RIGHT_ARROW){
            angle = 0f;
        }
        AttackFactory.createAttack(this, this.bulletList, angle, gameController);
    }

    //TODO test this method
    public boolean canPlayerShoot() {
        boolean result = false;
        Date date = new Date();
        long time = date.getTime();
        if (time - this.lastTimePlayerHasShot > this.shootCooldown * this.shootSpeedModificator) {
            result = true;
            this.lastTimePlayerHasShot = time;
        }
        return result;
    }

    //TODO test this method
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, this.getWidth(), this.getHeight());
    }

    public boolean isPlayerCollidesWithMap(Rectangle rec) {
        return this.map.isRectangleCollidesWithMap(rec);
    }

    public boolean isRectangleCollidesWithPlayers(Rectangle rec, ArrayList<Player> players) {
        return players.stream().anyMatch(player -> rec.intersects(player.getBounds()) && player.getUser().hasConnection());
    }

    public boolean isPlayersCollidesWithAnything(Rectangle rec, ArrayList<Player> players) {
        return isPlayerCollidesWithMap(rec) ||
                isRectangleCollidesWithPlayers(rec, players);
    }

    //TODO test this method USE reflection
    private double calculateSpeed(double deltaTime){
        double speed;
        this.moveSpeed = this.moveSpeed + (0.0003 * deltaTime) > (0.6 * this.MAX_SPEED) ? (0.6 * this.MAX_SPEED) : this.moveSpeed + (0.0003 * deltaTime);
        speed = (0.4 * this.MAX_SPEED) + this.moveSpeed;
        return speed;
    }

    //TODO test this method
    public void doDamage(int damage) {
        this.setHp(this.getHp() - damage);
    }

    //TODO test this method
    public void setWSAD(JSONObject wsad) {
        this.KEY_W = wsad.getBoolean("w");
        this.KEY_S = wsad.getBoolean("s");
        this.KEY_A = wsad.getBoolean("a");
        this.KEY_D = wsad.getBoolean("d");
        this.UP_ARROW = wsad.getBoolean("up");
        this.DOWN_ARROW = wsad.getBoolean("down");
        this.LEFT_ARROW = wsad.getBoolean("left");
        this.RIGHT_ARROW = wsad.getBoolean("right");
    }
    public void setSpecialKeys(JSONObject specialKeys){
        this.KEY_SPACE = specialKeys.getBoolean("space");
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
    public long getShootCooldown() {
        return this.shootCooldown;
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

    public int getCenterX() {
        return (int)this.getX() + (int) (this.getWidth() / 2.0);
    }

    public int getCenterY() {
        return (int)this.getY() + (int) (this.getHeight() / 2.0);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBulletType() {
        return bulletType;
    }

    public void setBulletType(String bulletType) {
        this.bulletType = bulletType;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public ArrayList<String> getCollectedItems() {
        return collectedItems;
    }

    public void setCollectedItems(ArrayList<String> collectedItems) {
        this.collectedItems = collectedItems;
    }

    //TODO test this method
    public boolean hasPlayerItem(String item){
        boolean result = false;
        for(String colletedItem : this.collectedItems){
            result = colletedItem.equals(item);
        }
        return result;
    }

    public long getShootSpeedModificator() {
        return shootSpeedModificator;
    }

    public void setShootSpeedModificator(long shootSpeedModificator) {
        this.shootSpeedModificator = shootSpeedModificator;
    }
    public double getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getY() {
        return this.y;
    }
    public GameController getGameController(){
        return this.gameController;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public String getHeadDirection() {
        return headDirection;
    }

    public void setHeadDirection(String headDirection) {
        this.headDirection = headDirection;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }
}