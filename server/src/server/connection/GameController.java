package server.connection;

import com.badlogic.gdx.Gdx;
import server.bullets.BulletList;
import server.opponents.OpponentList;
import server.pickups.APickup;
import server.pickups.PickupList;
import server.rooms.Room;
import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.opponents.AOpponent;
import java.awt.*;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameController implements Runnable {
    private static long deltaTime = 0L;
    private final long MILISECONDS_BEETWEEN_FRAMES = 3L;
    private boolean flag = true;
    public LogicMapHandler logicMapHandler;
    private Room room;
    public CopyOnWriteArrayList<User> usersInRoom;
    public PickupList pickupList;
    public BulletList bulletList;
    public OpponentList opponentList;

    public GameController(Room room) {
        this.room = room;
        this.usersInRoom = room.getUsersInRoom();
        this.logicMapHandler = new LogicMapHandler(this);
        this.pickupList = new PickupList(usersInRoom);
        this.bulletList = room.getBulletList();
        this.opponentList = room.getOpponentList();
        this.opponentList.initializeWithGameController(this);
        this.room.setGameController(this);
    }

    public void run() {
        long timeBefore;
        long timeAfter;
        while (flag) {
            timeBefore = new Date().getTime();
            this.doGameLoop();
            timeAfter = new Date().getTime();
            deltaTime = timeAfter - timeBefore;
            this.sleepIfNecessary();
            deltaTime = new Date().getTime() - timeBefore;
        }
    }

    public void stopThread() {
        this.flag = false;
    }

    private void sleepIfNecessary() {
        if (deltaTime < MILISECONDS_BEETWEEN_FRAMES) {
            try {
                Thread.sleep(MILISECONDS_BEETWEEN_FRAMES - deltaTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doGameLoop() {
        updatePlayerPosition();
        updatePickups();
        doUdpSends();
        doTcpSends();
    }

    private void doUdpSends() {
        sendPlayerDataPackage();
        updateBulletsAndSendBulletPositionPackage();
        updateOpponentsAndSendOpponentDataPackage();
    }

    private void doTcpSends() {

    }

    private void updatePlayerPosition() {
        for (User user : usersInRoom) {
            user.getPlayer().update(this.usersInRoom, deltaTime);
        }
    }

    private void updatePickups(){
        for(APickup pickup : this.pickupList.getPickups()){
            for(User user : this.usersInRoom){
               if(pickup.getBounds().intersects(user.getPlayer().getBounds())){
                   pickup.modifyPlayer(user.getPlayer());
                   this.pickupList.deletePickup(pickup);
               }
            }
        }
    }

    private void updateOpponentsAndSendOpponentDataPackage() {
        String message;
        for (AOpponent opponent : this.opponentList.getOpponents()) {
            opponent.update(deltaTime);
            message = "updateOpponentData:" +
                    opponent.getId() + ":" +
                    (int) opponent.getX() + ":" +
                    (int) opponent.getY() + ":" +
                    opponent.getHp();

            UdpServer.sendUdpMsgToAllUsersInRoom(message, usersInRoom);
        }
    }

    private void updateBulletsAndSendBulletPositionPackage() {
        String message;
        for (ABullet bullet : this.bulletList.getBullets()) {
            bullet.update(deltaTime, usersInRoom);
            message = "updateBulletPosition:" +
                    bullet.getId() + ":" +
                    bullet.getX() + ":" +
                    bullet.getY();

            UdpServer.sendUdpMsgToAllUsersInRoom(message, usersInRoom);
        }
    }

    public void sendPlayerDataPackage() {
        for (User u : usersInRoom) {
            String message = "updatePosition:" +
                    u.getName() + ":" +
                    u.getPlayer().getX() + ":" +
                    u.getPlayer().getY() + ":" +
                    u.getPlayer().getHp() + ":" +
                    u.getPlayer().getActiveMovementKey() + ":" +
                    u.getPlayer().isMoving();
            UdpServer.sendUdpMsgToAllUsersInRoom(message, usersInRoom);
        }
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
        String message;
        for (AOpponent opponent : this.opponentList.getOpponents()) {
            message = "createOpponent:" +
                    opponent.getType() + ":" +
                    opponent.getId();

            TcpServer.sendTcpMsgToAllUsersInRoom(message, room.getUsersInRoom());
        }
    }

    public void changeMap(){
        String nextMap = this.logicMapHandler.getEventList().getNextMap();
        for(AOpponent opponent : this.opponentList.getOpponents()){
            this.opponentList.deleteOpponent(opponent);
        }
        for(APickup pickup : this.pickupList.getPickups()){
            this.pickupList.deletePickup(pickup);
        }
        this.logicMapHandler.LoadMap(nextMap);
        this.setPlayersPosition();
        this.sendPlayerDataPackage();
        this.spawnAllOpponents();
        String msg = "changeLevel:" + nextMap + ":";
        TcpServer.sendTcpMsgToAllUsersInRoom(msg, this.room.getUsersInRoom());
    }
}
