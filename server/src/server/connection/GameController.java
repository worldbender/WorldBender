package server.connection;

import RoomsController.Room;
import RoomsController.RoomList;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameController implements Runnable {
    private static long deltaTime = 0L;
    private final long MILISECONDS_BEETWEEN_FRAMES = 3L;
    private LogicMapHandler logicMapHandler;
    private List<Room> rooms;
    private Room room;
    private CopyOnWriteArrayList<User> usersInRoom;

    public GameController(Room room) {
        this.rooms = RoomList.getInstance();
        this.room = room;
        this.usersInRoom = room.getUsersInRoom();
        this.logicMapHandler = new LogicMapHandler();
    }

    public void setRoom(Room room){
        this.room = room;
        this.usersInRoom = room.getUsersInRoom();
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
        for (User user : usersInRoom){
            user.getPlayer().update(logicMapHandler, usersInRoom, deltaTime);
        }
    }

    private void updateOpponentsAndSendOpponentDataPackage() {
        String message;
        for (AOpponent opponent : room.opponentList.getOpponents()) {
            opponent.update(deltaTime, logicMapHandler, usersInRoom, room.bulletList);
            message = "updateOpponentData:" + opponent.getId() + ":" + opponent.getX() + ":" + opponent.getY() + ":" + opponent.getHp();
            UdpServer.sendUdpMsgToAllUsersInRoom(message, usersInRoom);
        }
    }

    private void updateBulletsAndSendBulletPositionPackage() {
        String message;
        for (ABullet bullet : room.bulletList.getBullets()) {
            bullet.update(deltaTime, logicMapHandler, usersInRoom, room.opponentList, room.bulletList);
            message = "updateBulletPosition:" + bullet.getId() + ":" + bullet.getX() + ":" + bullet.getY();
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

    private void informClientsAboutDeadBullets() {
        String message;
        for (ABullet bullet : room.bulletList.getDeadBullets()) {
            message = "deleteBullet:" + bullet.getId();
            UdpServer.sendUdpMsgToAllUsersInRoom(message, usersInRoom);
        }
        room.bulletList.flushDeadBullets();
    }
    private void informClientsAboutDeadOpponents() {
        String message;
        for (AOpponent opponent : room.opponentList.getDeadOpponenets()) {
            message = "deleteOpponent:" + opponent.getId();
            TcpServer.sendTcpMsgToAllUsersInRoom(message, usersInRoom);
        }
        room.opponentList.flushDeadAOpponents();
    }
    private void informClientsAboutNewBullets(){
        String message;
        for (ABullet bullet : room.bulletList.getBulletsToCreate()) {
            message = "createBullet:" + bullet.getType() + ":" + bullet.getId() + ":" + bullet.getAngle();
            UdpServer.sendUdpMsgToAllUsersInRoom(message, usersInRoom);
        }
        room.bulletList.flushBulletsToCreate();
    }
}
