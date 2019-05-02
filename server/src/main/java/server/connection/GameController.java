package server.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import server.bullets.BulletList;
import server.opponents.OpponentList;
import server.pickups.APickup;
import server.pickups.PickupList;
import server.rooms.Room;
import server.logicmap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.opponents.AOpponent;

import java.awt.*;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameController implements Runnable {
    private static Logger logger = LogManager.getLogger(GameController.class.getName());
    private static long deltaTime = 0L;
    private final long MILLISECONDS_BETWEEN_FRAMES = 3L;
    private boolean flag = true;
    public boolean hasStarted = false;
    public LogicMapHandler logicMapHandler;
    private Room room;
    public CopyOnWriteArrayList<User> usersInRoom;
    public PickupList pickupList;
    public BulletList bulletList;
    public OpponentList opponentList;

    public GameController(Room room) {
        this.room = room;
        this.usersInRoom = room.getUsersInRoom();
        this.pickupList = new PickupList(usersInRoom);
        this.logicMapHandler = new LogicMapHandler(this);
        this.bulletList = room.getBulletList();
        this.opponentList = room.getOpponentList();
        this.opponentList.initializeWithGameController(this);
        this.room.setGameController(this);
    }
    public GameController(Room room, LogicMapHandler logicMapHandler) {
        this.room = room;
        this.usersInRoom = room.getUsersInRoom();
        this.pickupList = new PickupList(usersInRoom);
        this.logicMapHandler = logicMapHandler;
        this.bulletList = room.getBulletList();
        this.opponentList = room.getOpponentList();
    }

    public void run() {
        long timeBefore;
        long timeAfter;

        //Dont start game loop before everthing is set up
        while(!this.hasStarted){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.error(e.toString(), e);
                Thread.currentThread().interrupt();
            }
        }

        while (flag) {
            if (true) {
                timeBefore = new Date().getTime();
                this.doGameLoop();
                timeAfter = new Date().getTime();
                deltaTime = timeAfter - timeBefore;
                this.sleepIfNecessary();
                deltaTime = new Date().getTime() - timeBefore;
            }
        }
    }

    public void stopThread() {
        this.flag = false;
    }

    private void sleepIfNecessary() {
        if (deltaTime < MILLISECONDS_BETWEEN_FRAMES) {
            try {
                Thread.sleep(MILLISECONDS_BETWEEN_FRAMES - deltaTime);
            } catch (InterruptedException e) {
                logger.error(e.toString(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void doGameLoop() {
        updatePlayerPosition();
        updatePickups();
        updateOpponents();
        updateBullets();
        doUdpSends();
    }

    private void doUdpSends() {
        JSONObject data = new JSONObject();
        JSONArray players = getPlayersData();
        JSONArray bullets = getBulletsData();
        JSONArray opponents = getOpponentsData();

        data.put("msg", "game")
                .put("content", new JSONObject().put("players", players).put("bullets", bullets).put("opponents", opponents));
        UdpServer.sendUdpMsgToAllUsersInRoom(data.toString(), usersInRoom);
    }

    private void updatePlayerPosition() {
        for (User user : usersInRoom) {
            user.getPlayer().update(this.usersInRoom, deltaTime);
        }
    }

    private void updatePickups() {
        for (APickup pickup : this.pickupList.getPickups()) {
            for (User user : this.usersInRoom) {
                if (pickup.getBounds().intersects(user.getPlayer().getBounds()) && !pickup.isBlocked()) {
                    pickup.modifyPlayer(user.getPlayer());
                    this.pickupList.deletePickup(pickup);
                }
            }
        }
    }

    private void updateOpponents() {
        for (AOpponent opponent : this.opponentList.getOpponents()) {
            opponent.update(deltaTime);
        }
    }

    private void updateBullets() {
        for (ABullet bullet : this.bulletList.getBullets()) {
            bullet.update(deltaTime, usersInRoom);
        }
    }

    public JSONArray getOpponentsData() {

        JSONArray opponentsList = new JSONArray();

        for (AOpponent opponent : this.opponentList.getOpponents()) {
            JSONObject opponentData = new JSONObject()
                    .put("id", opponent.getId())
                    .put("x", opponent.getX())
                    .put("y", opponent.getY())
                    .put("hp", opponent.getHp())
                    .put("type", opponent.getType());

            opponentsList.put(opponentData);
        }

        return opponentsList;
    }

    public JSONArray getBulletsData() {

        JSONArray bulletsList = new JSONArray();

        for (ABullet bullet : this.bulletList.getBullets()) {
            JSONObject bulletData = new JSONObject()
                    .put("id", bullet.getId())
                    .put("x", bullet.getX())
                    .put("y", bullet.getY());

            bulletsList.put(bulletData);
        }

        return bulletsList;
    }

    public JSONArray getPlayersData() {

        JSONArray playersList = new JSONArray();

        for (User user : usersInRoom) {
            JSONObject playerData = new JSONObject()
                    .put("name", user.getName())
                    .put("x", user.getPlayer().getX())
                    .put("y", user.getPlayer().getY())
                    .put("hp", user.getPlayer().getHp())
                    .put("activeMovementKey", user.getPlayer().getActiveMovementKey())
                    .put("headDirection", user.getPlayer().getHeadDirection())
                    .put("isMoving", user.getPlayer().isMoving());

            playersList.put(playerData);
        }

        return playersList;
    }

    public void setPlayersPosition() {
        for (User user : room.getUsersInRoom()) {
            Point nextFreePoint = logicMapHandler.getNextPlayerSpawnPoint();
            user.getPlayer().setX((int) nextFreePoint.getX());
            user.getPlayer().setY((int) nextFreePoint.getY());
        }
    }

    public void spawnAllOpponents() {
        this.logicMapHandler.getEventList().spawnAllOpponents(this.opponentList);
    }

    public void changeMap() {
        String nextMap = this.logicMapHandler.getEventList().getNextMap();
        for (AOpponent opponent : this.opponentList.getOpponents()) {
            this.opponentList.deleteOpponent(opponent);
        }
        for (APickup pickup : this.pickupList.getPickups()) {
            this.pickupList.deletePickup(pickup);
        }
        this.logicMapHandler.loadMap(nextMap);
        this.setPlayersPosition();
        this.spawnAllOpponents();

        JSONArray players = this.getPlayersData();
        JSONArray opponents = this.getOpponentsData();

        JSONObject msg = new JSONObject().put("msg", "changeLevel")
                .put("content", new JSONObject().put("map", nextMap).put("opponents", opponents).put("players", players));
        TcpServer.sendTcpMsgToAllUsersInRoom(msg, this.room.getUsersInRoom());
    }
}
