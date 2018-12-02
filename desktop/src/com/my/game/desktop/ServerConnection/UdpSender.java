package com.my.game.desktop.ServerConnection;

import com.my.game.desktop.ExistingUsers;
import com.my.game.desktop.LogicMapHandler;
import com.my.game.desktop.SOpponents.AOpponent;
import com.my.game.desktop.SOpponents.OpponentList;
import com.my.game.desktop.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import com.my.game.desktop.SBullets.ABullet;
import com.my.game.desktop.SBullets.BulletFabric;
import com.my.game.desktop.SBullets.BulletList;

public class UdpSender implements Runnable{
    private DatagramSocket socket;
    private Map<String, User> existingUsers;
    LogicMapHandler logicMapHandler;
    public UdpSender(DatagramSocket socket){
        this.existingUsers = ExistingUsers.getInstance();
        this.socket = socket;
        logicMapHandler = new LogicMapHandler();
    }
    public void run(){
        while(true){
            sendPlayerPositionPackage();
            sendBulletPositionPackage();
            sendOpponentPositionPackage();
            informClientsAboutDeadBullets();
            informClientsAboutDeadOpponents();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendOpponentPositionPackage() {
        String message;
        for (User current : existingUsers.values()) {
            for(AOpponent opponent : OpponentList.getOpponents()){
                opponent.update(5.0);
                message = "updateOpponentPosition:"+opponent.getId() + ":" + opponent.getX()+ ":"+ opponent.getY();
                if(current.getConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
    }

    private void sendBulletPositionPackage() {
        String message;
        for (User current : existingUsers.values()) {
            for(ABullet bullet : BulletList.getBullets()){
                bullet.update(5.0, logicMapHandler);
                message = "updateBulletPosition:"+bullet.getId() + ":" + bullet.getX()+ ":"+ bullet.getY();
                if(current.getConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
    }

    public void sendPlayerPositionPackage(){
        for (User current : existingUsers.values()) {
            for (User u : existingUsers.values()) {
                String message = "updatePosition:"+u.getName() + ":" + u.getPlayer().getX()+ ":"+ u.getPlayer().getY();
                if(current.getConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
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
    private void informClientsAboutDeadBullets(){
        String message;
        for(ABullet bullet : BulletList.getDeadBullets()){
            message = "deleteBullet:" + bullet.getId();
            for (User current : existingUsers.values()) {
                if(current.getConnection())
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
                if(current.getConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
        OpponentList.flushDeadOpponents();
    }
}
