package com.my.game.desktop.ServerConnection;

import com.my.game.desktop.*;
import com.my.game.desktop.SBullets.ABullet;
import com.my.game.desktop.SBullets.BulletFabric;
import com.my.game.desktop.SBullets.BulletList;
import com.my.game.desktop.SOpponents.AOpponent;
import com.my.game.desktop.SOpponents.OpponentFabric;
import com.my.game.desktop.SOpponents.OpponentList;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UdpServer extends Thread {
    private final static int PORT = 7331;
    private final static int BUFFER = 1024;
    private DatagramSocket socket;
    private List<Room> rooms;
    private Map<String, User> existingUsers;
    private LogicMapHandler logicMapHandler;
    public UdpServer() throws IOException {
        this.socket = new DatagramSocket(PORT);
        this.existingUsers = ExistingUsers.getInstance();
        this.rooms = RoomList.getInstance();
        logicMapHandler = new LogicMapHandler();
    }

    public void run() {
        byte[] buf = new byte[BUFFER];
        while (true) {
            try {
                Arrays.fill(buf, (byte)0);
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                readPackage(packet, buf);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePlayerPosition(String id, String content){
        User currentUser = existingUsers.get(id);

        currentUser.getPlayer().setPosition(content, logicMapHandler, existingUsers);
        String message = "updatePosition:"+currentUser.getName() + ":" + currentUser.getPlayer().getX()+ ":"+ currentUser.getPlayer().getY();

        for (User current : existingUsers.values()) {
            if(current.getConnection())
                sendPackage(message, current.getAddress(), current.getUdpPort());
        }
    }

    private void updatePlayerPosition(String id){
        User currentUser = existingUsers.get(id);
        String message = "updatePosition:"+currentUser.getName() + ":" + currentUser.getPlayer().getX()+ ":"+ currentUser.getPlayer().getY();

        for (User current : existingUsers.values()) {
            if(current.getConnection())
                sendPackage(message, current.getAddress(), current.getUdpPort());
        }
    }
    private void updateBulletsPosition(){
        String message;
        for (User current : existingUsers.values()) {
            for(ABullet bullet : BulletList.getBullets()){
                bullet.update(5.0);
                message = "updateBulletPosition:"+bullet.getId() + ":" + bullet.getX()+ ":"+ bullet.getY();
                if(current.getConnection())
                    sendPackage(message, current.getAddress(), current.getUdpPort());
            }
        }
    }
    private void createBullet(String id, String content){
        User currentUser = existingUsers.get(id);
        String[] splitedContent = content.split(":");
        String bulletType = splitedContent[1];
        String angle = splitedContent[2];
        ABullet newBullet = BulletFabric.createBullet(bulletType, currentUser.getPlayer().getX(), currentUser.getPlayer().getY(), Float.parseFloat(angle));
        BulletList.addBullet(newBullet);
        String message = "createBullet:" + newBullet.getType() + ":" + newBullet.getId() + ":" + newBullet.getAngle();
        for(User user : existingUsers.values()){
            if(user.getConnection())
                sendPackage(message, user.getAddress(), user.getUdpPort());
        }
    }
    private void createOpponent(){
        String opponentType = "Schopenheuer";
        AOpponent newOpponent = OpponentFabric.createOpponent(opponentType);
        OpponentList.addOpponent(newOpponent);
        String message = "createOpponent:" + newOpponent.getType() + ":" + newOpponent.getId();
        for(User user : existingUsers.values()){
            if(user.getConnection())
                sendPackage(message, user.getAddress(), user.getUdpPort());
        }
    }

    private void updateOpponentsPosition(){
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

    private void initNewPlayer(String id, DatagramPacket packet){
        sendPackage("Server: Connected", packet.getAddress(), packet.getPort());

        for(User user : existingUsers.values()){
            sendPackage("newPlayer:player" + (existingUsers.size()-1), user.getAddress(), user.getUdpPort());
            updatePlayerPosition(id);
        }

        for (User current : existingUsers.values()) {
            String message = "init:"+current.getName() + ":" + current.getPlayer().getX() + ":" + current.getPlayer().getY();
            if(current.getConnection())
                sendPackage(message, packet.getAddress(), packet.getPort());
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

    private void readPackage(DatagramPacket packet, byte[] buf){
        String content = new String(buf);
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();
        String id = clientAddress.toString() + "," + clientPort;
        System.out.println(id + ":" + content);
        String[] splitedArray = content.split(":");
        if ("greetings".equals(splitedArray[0])) {
            initNewPlayer(id, packet);
        }else if("createBullet".equals(splitedArray[0])){
            createBullet(id, content);
        }
        else {
            updatePlayerPosition(id, content);
            updateBulletsPosition();
        }
    }
}
