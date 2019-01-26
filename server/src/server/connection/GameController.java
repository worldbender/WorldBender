package server.connection;

import server.rooms.Room;
import server.rooms.RoomList;
import server.LogicMap.LogicMapHandler;
import server.User;
import server.bullets.ABullet;
import server.opponents.AOpponent;

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

    public GameController(Room room) {
        this.rooms = RoomList.getInstance();
        this.room = room;
        this.usersInRoom = room.getUsersInRoom();
        this.logicMapHandler = new LogicMapHandler();
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

    public void stopThread(){
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
        for (AOpponent opponent : room.getOpponentList().getOpponents()) {
            opponent.update(deltaTime, logicMapHandler, usersInRoom, room.getBulletList());
            message = "updateOpponentData:" +
                    opponent.getId() + ":" +
                    opponent.getX() + ":" +
                    opponent.getY() + ":" +
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

    private void informClientsAboutDeadBullets() {
        String message;
        for (ABullet bullet : room.getBulletList().getDeadBullets()) {
            message = "deleteBullet:" +
                    bullet.getId();

            UdpServer.sendUdpMsgToAllUsersInRoom(message, usersInRoom);
        }
        room.getBulletList().flushDeadBullets();
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
    private void informClientsAboutNewBullets(){
        String message;
        for (ABullet bullet : room.getBulletList().getBulletsToCreate()) {
            message = "createBullet:" +
                    bullet.getType() + ":" +
                    bullet.getId() + ":" +
                    bullet.getAngle();

            UdpServer.sendUdpMsgToAllUsersInRoom(message, usersInRoom);
        }
        room.getBulletList().flushBulletsToCreate();
    }

}
