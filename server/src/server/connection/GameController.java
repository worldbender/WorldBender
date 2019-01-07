package server.connection;

import server.ExistingUsers;
import server.LogicMapHandler;
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

public class GameController implements Runnable{
    private static long deltaTime = 0L;
    private final long MILISECONDS_BEETWEEN_FRAMES = 16L;
    private LogicMapHandler logicMapHandler;
    private Map<String, User> existingUsers;
    UdpServer udpSender;
    TcpClientThread tcpSender;
    private DatagramSocket socket;


    public GameController(DatagramSocket socket){
        this.existingUsers = ExistingUsers.getInstance();
        logicMapHandler = new LogicMapHandler();
        /*this.udpSender = udpSender;
        this.tcpSender = tcpSender;*/
        this.socket = socket;
    }

    public void run(){
        long timeBefore;
        long timeAfter;
        while(true){
            timeBefore = new Date().getTime();
            this.doGameLoop();
            this.sleepIfNecessary();
            timeAfter = new Date().getTime();
            deltaTime = timeAfter - timeBefore;
        }
    }

    public void sendPackage(String message, InetAddress clientAddress, int clientPort){
        DatagramPacket packet;
        byte[] data = message.getBytes();
        packet = new DatagramPacket(data, data.length, clientAddress, clientPort);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sleepIfNecessary(){
        if(deltaTime < MILISECONDS_BEETWEEN_FRAMES){
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doGameLoop(){
        sendPlayerDataPackage();
        sendBulletPositionPackage();
        sendOpponentDataPackage();
        informClientsAboutDeadBullets();
        informClientsAboutDeadOpponents();
    }

    private void sendOpponentDataPackage() {
        String message;
        for (User current : existingUsers.values()) {
            for(AOpponent opponent : OpponentList.getOpponents()){
                opponent.update(deltaTime, logicMapHandler);
                message = "updateOpponentData:"+opponent.getId() + ":" + opponent.getX()+ ":"+ opponent.getY() + ":" + opponent.getHp();
                if(current.hasConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
    }

    private void sendBulletPositionPackage() {
        String message;
        for (User current : existingUsers.values()) {
            for(ABullet bullet : BulletList.getBullets()){
                bullet.update(deltaTime, logicMapHandler);
                message = "updateBulletPosition:"+bullet.getId() + ":" + bullet.getX()+ ":"+ bullet.getY();
                if(current.hasConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
    }

    public void sendPlayerDataPackage(){
        for (User current : existingUsers.values()) {
            for (User u : existingUsers.values()) {
                String message = "updatePosition:" +
                        u.getName() + ":" +
                        u.getPlayer().getX() + ":" +
                        u.getPlayer().getY() + ":" +
                        u.getPlayer().getHp() + ":" +
                        u.getPlayer().getActiveMovementKey() + ":" +
                        u.getPlayer().isMoving();
                if(current.hasConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
    }

    private void informClientsAboutDeadBullets(){
        String message;
        for(ABullet bullet : BulletList.getDeadBullets()){
            message = "deleteBullet:" + bullet.getId();
            for (User current : existingUsers.values()) {
                if(current.hasConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
        BulletList.flushDeadBullets();
    }
    private void informClientsAboutDeadOpponents(){
        String message;
        for(AOpponent opponent : OpponentList.getDeadOpponents()){
            message = "deleteOpponent:" + opponent.getId();
            for (User current : existingUsers.values()) {
                if(current.hasConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
        OpponentList.flushDeadOpponents();
    }

    public static long getDeltaTime() {
        return deltaTime;
    }
}
