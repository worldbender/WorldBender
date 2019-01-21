package server.connection;

import RoomsController.Room;
import server.ExistingUsers;
import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.bullets.BulletList;
import server.opponents.AOpponent;
import server.opponents.OpponentList;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameController implements Runnable {
    private static long deltaTime = 0L;
    private final long MILISECONDS_BEETWEEN_FRAMES = 3L;
    private LogicMapHandler logicMapHandler;
    private Map<String, User> existingUsers;
    private CopyOnWriteArrayList<User> usersInRoom;

    public GameController() {
        this.existingUsers = ExistingUsers.getInstance();
        this.logicMapHandler = new LogicMapHandler();
    }

    public GameController(Room room) {
        this.usersInRoom = room.getUsersInRoom();
        this.logicMapHandler = new LogicMapHandler();
    }

    public void run() {
        long timeBefore;
        long timeAfter;
        while (true) {
            timeBefore = new Date().getTime();
            this.doGameLoop();
            timeAfter = new Date().getTime();
            deltaTime = timeAfter - timeBefore;
            this.sleepIfNecessary();
            deltaTime = new Date().getTime() - timeBefore;
        }
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
        doUdpSends();
        doTcpSends();
    }

    private void doUdpSends(){
        sendPlayerDataPackage();
        updateBulletsAndSendBulletPositionPackage();
        updateOpponentsAndSendOpponentDataPackage();
        informClientsAboutDeadBullets();
        informClientsAboutNewBullets();
    }
    private void doTcpSends(){
        informClientsAboutDeadOpponents();
    }

    private void updatePlayerPosition(){
        //System.out.println(Gdx.graphics.getDeltaTime());
        for (User user : existingUsers.values()){
            user.getPlayer().update(logicMapHandler, existingUsers, deltaTime);
        }
    }

    private void updateOpponentsAndSendOpponentDataPackage() {
        String message;
        for (AOpponent opponent : OpponentList.getOpponents()) {
            opponent.update(deltaTime, logicMapHandler, existingUsers);
            message = "updateOpponentData:" + opponent.getId() + ":" + opponent.getX() + ":" + opponent.getY() + ":" + opponent.getHp();
            UdpServer.sendUdpMsgToAllUsers(message, existingUsers);
        }
    }

    private void updateBulletsAndSendBulletPositionPackage() {
        String message;
        for (ABullet bullet : BulletList.getBullets()) {
            bullet.update(deltaTime, logicMapHandler, existingUsers);
            message = "updateBulletPosition:" + bullet.getId() + ":" + bullet.getX() + ":" + bullet.getY();
            UdpServer.sendUdpMsgToAllUsers(message, existingUsers);
        }
    }

    public void sendPlayerDataPackage() {
        for (User u : existingUsers.values()) {
            String message = "updatePosition:" +
                    u.getName() + ":" +
                    u.getPlayer().getX() + ":" +
                    u.getPlayer().getY() + ":" +
                    u.getPlayer().getHp() + ":" +
                    u.getPlayer().getActiveMovementKey() + ":" +
                    u.getPlayer().isMoving();
            UdpServer.sendUdpMsgToAllUsers(message, existingUsers);
        }
    }

    private void informClientsAboutDeadBullets() {
        String message;
        for (ABullet bullet : BulletList.getDeadBullets()) {
            message = "deleteBullet:" + bullet.getId();
            UdpServer.sendUdpMsgToAllUsers(message, existingUsers);
        }
        BulletList.flushDeadBullets();
    }
    private void informClientsAboutDeadOpponents() {
        String message;
        for (AOpponent opponent : OpponentList.getDeadOpponenets()) {
            message = "deleteOpponent:" + opponent.getId();
            TcpServer.sendTcpMsgToAllUsers(message, existingUsers);
        }
        OpponentList.flushDeadAOpponents();
    }
    private void informClientsAboutNewBullets(){
        String message;
        for (ABullet bullet : BulletList.getBulletsToCreate()) {
            message = "createBullet:" + bullet.getType() + ":" + bullet.getId() + ":" + bullet.getAngle();
            UdpServer.sendUdpMsgToAllUsers(message, existingUsers);
        }
        BulletList.flushBulletsToCreate();
    }
}
