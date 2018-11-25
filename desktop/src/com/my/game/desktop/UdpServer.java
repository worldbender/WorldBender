package com.my.game.desktop;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
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
        } else {
            updatePlayerPosition(id, content);
        }
    }
}
