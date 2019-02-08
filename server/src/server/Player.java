package server;

import server.LogicMap.LogicMapHandler;
import server.bullets.AtackFabric;
import server.bullets.BulletList;
import server.connection.GameController;

import javax.jws.soap.SOAPBinding;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player {

    private int x = 500;
    private int y = 500;
    private int width;
    private int height;
    private int hp = 100;
    public final int MAX_HP = 100;
    private double moveSpeed = 0.65;
    private long shootCooldown = 100L;
    private long lastTimePlayerHasShot = 0L;
    private long shootSpeedModificator = 1L;
    private String activeMovementKey = "DOWN";
    private String bulletType = "Tear";
    private String weaponType = "Normal";
    private ArrayList<String> collectedItems;
    private boolean isMoving = false;
    public static final int PLAYER_TEXTURE_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_WIDTH"));
    public static final int PLAYER_TEXTURE_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_HEIGHT"));
    private float scale = 2f;
    private User user;
    public boolean KEY_W = false;
    public boolean KEY_S = false;
    public boolean KEY_A = false;
    public boolean KEY_D = false;
    private LogicMapHandler map;
    private BulletList bulletList;
    private CopyOnWriteArrayList<User> usersInRoom;
    private GameController gameController;

    public Player(User user) {
        this.setWidth((int) (PLAYER_TEXTURE_WIDTH * scale));
        this.setHeight((int) (PLAYER_TEXTURE_HEIGHT * scale));
        this.user = user;
        this.collectedItems = new ArrayList<String>();
    }

    public Player(User user, GameController gameController){
        this(user);
        this.map = gameController.logicMapHandler;
        this.bulletList = gameController.bulletList;
        this.usersInRoom = gameController.usersInRoom;
        this.gameController = gameController;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.getWidth(), this.getHeight());
    }



    public boolean isPlayerCollidesWithMap(Rectangle rec) {
        return this.map.isRectangleCollidesWithMap(rec);
    }

    public void shoot(float angle) {
        AtackFabric.createAtack(this, this.bulletList, angle, gameController);
    }

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

    public boolean isRectangleCollidesWithPlayers(Rectangle rec, ArrayList<Player> players) {
        boolean result = false;
        for (Player player : players) {
            if (rec.intersects(player.getBounds()) && player.getUser().hasConnection()) {
                result = true;
            }
        }
        return result;
    }

    public boolean isPlayersCollidesWithAnything(Rectangle rec, ArrayList<Player> players) {
        return isPlayerCollidesWithMap(rec) ||
                isRectangleCollidesWithPlayers(rec, players);
    }

    public void setActiveMovementKeyByAngle(String angle) {
        float parsedAngle = Float.parseFloat(angle);
        if (Math.abs(parsedAngle - Math.PI) < 0.001f) {
            setActiveMovementKey("LEFT");
        } else if (Math.abs(parsedAngle - Math.PI / 2) < 0.001f) {
            setActiveMovementKey("UP");
        } else if (Math.abs(parsedAngle) < 0.001f) {
            setActiveMovementKey("RIGHT");
        } else {
            setActiveMovementKey("DOWN");
        }
    }

    public void update(CopyOnWriteArrayList<User> usersInRoom, double deltaTime) {
        Rectangle playersNewBoundsRectangle;
        ArrayList<Player> players = new ArrayList<Player>();
        int currentShift = (int) (deltaTime * this.moveSpeed);

        for (User user : usersInRoom) {
            if (user.getPlayer() != this) {
                players.add(user.getPlayer());
            }
        }
        if (this.isMoving) {
            if (this.KEY_W) {
                playersNewBoundsRectangle = new Rectangle(this.x, this.y + currentShift, this.getWidth(), this.getHeight());
                if (!isPlayersCollidesWithAnything(playersNewBoundsRectangle, players)) {
                    this.y += currentShift;
                }
            }
            if (this.KEY_S) {
                playersNewBoundsRectangle = new Rectangle(this.x, this.y - currentShift, this.getWidth(), this.getHeight());
                if (!isPlayersCollidesWithAnything(playersNewBoundsRectangle, players)) {
                    this.y -= currentShift;
                }
            }
            if (this.KEY_A) {
                playersNewBoundsRectangle = new Rectangle(this.x - currentShift, this.y, this.getWidth(), this.getHeight());
                if (!isPlayersCollidesWithAnything(playersNewBoundsRectangle, players)) {
                    this.x -= currentShift;
                }
            }
            if (this.KEY_D) {
                playersNewBoundsRectangle = new Rectangle(this.x + currentShift, this.y, this.getWidth(), this.getHeight());
                if (!isPlayersCollidesWithAnything(playersNewBoundsRectangle, players)) {
                    this.x += currentShift;
                }
            }
        }
    }

    public void doDamage(int damage) {
        this.setHp(this.getHp() - damage);
    }

    public void setWSAD(String wsad) {
        String splitedWsad[] = wsad.split(",");
        this.KEY_W = Boolean.parseBoolean(splitedWsad[0]);
        this.KEY_S = Boolean.parseBoolean(splitedWsad[1]);
        this.KEY_A = Boolean.parseBoolean(splitedWsad[2]);
        this.KEY_D = Boolean.parseBoolean(splitedWsad[3]);
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
        return this.getX() + (int) (this.getWidth() / 2.0);
    }

    public int getCenterY() {
        return this.getY() + (int) (this.getHeight() / 2.0);
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
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }
    public GameController getGameController(){
        return this.gameController;
    }
}