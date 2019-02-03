package server.connection;

import server.pickups.APickup;
import server.pickups.PickupList;
import server.rooms.Room;
import server.rooms.RoomList;
import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.opponents.AOpponent;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameController implements Runnable {
    private static long deltaTime = 0L;
    private final long MILISECONDS_BEETWEEN_FRAMES = 3L;
    private boolean flag = true;
    private LogicMapHandler logicMapHandler;
    private Room room;
    private List<Room> rooms;
    private CopyOnWriteArrayList<User> usersInRoom;
    private PickupList pickupList;

    public GameController(Room room) {
        this.rooms = RoomList.getInstance();
        this.room = room;
        this.usersInRoom = room.getUsersInRoom();
        this.logicMapHandler = new LogicMapHandler();
        this.room.setGameController(this);
        this.pickupList = new PickupList(usersInRoom);
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
        informClientsAboutDeadOpponents();
    }

    private void updatePlayerPosition() {
        for (User user : usersInRoom) {
            user.getPlayer().update(logicMapHandler, usersInRoom, deltaTime);
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
        for (AOpponent opponent : room.getOpponentList().getOpponents()) {
            opponent.update(deltaTime, logicMapHandler, usersInRoom, room.getBulletList(), room.getOpponentList(), this.pickupList);
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
        for (ABullet bullet : room.getBulletList().getBullets()) {
            bullet.update(deltaTime, logicMapHandler, usersInRoom, room.getOpponentList(), room.getBulletList());
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

    private void informClientsAboutDeadOpponents() {
        String message;
        for (AOpponent opponent : room.getOpponentList().getDeadOpponenets()) {
            message = "deleteOpponent:" +
                    opponent.getId();

            TcpServer.sendTcpMsgToAllUsersInRoom(message, usersInRoom);
        }
        room.getOpponentList().flushDeadAOpponents();
    }

    public void setPlayersPosition() {
        for (User user : room.getUsersInRoom()) {
            Point nextFreePoint = logicMapHandler.getNextPlayerSpawnPoint();
            user.getPlayer().setX((int) nextFreePoint.getX());
            user.getPlayer().setY((int) nextFreePoint.getY());
        }
    }

    public void spawnAllOpponents() {
        this.logicMapHandler.getEventList().spawnAllOpponents(this.room.getOpponentList());
        String message;
        for (AOpponent opponent : this.room.getOpponentList().getOpponents()) {
            message = "createOpponent:" +
                    opponent.getType() + ":" +
                    opponent.getId();

            TcpServer.sendTcpMsgToAllUsersInRoom(message, room.getUsersInRoom());
        }
    }
}
