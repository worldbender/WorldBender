package com.my.game.desktop.ServerConnection;

import com.my.game.desktop.ExistingUsers;
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
    public UdpSender(DatagramSocket socket){
        this.existingUsers = ExistingUsers.getInstance();
        this.socket = socket;
    }
    public void run(){
        while(true){
            sendPlayerPositionPackage();
            sendBulletPositionPackage();
            sendOpponentPositionPackage();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendOpponentPositionPackage() {

    }

    private void sendBulletPositionPackage() {
        String message;
        for (User current : existingUsers.values()) {
            for(ABullet bullet : BulletList.getBullets()){
                //bullet.update(5.0, logicMapHandler);
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
}
