package server.connection;

import server.bullets.ABullet;
import server.bullets.BulletFabric;
import server.bullets.BulletList;
import server.*;
import server.opponents.AOpponent;
import server.opponents.OpponentFabric;
import server.opponents.OpponentList;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UdpServer extends Thread {
    private final static int PORT = Integer.parseInt(Properties.loadConfigFile("PortUdp"));
    private final static int BUFFER = 1024;
    private DatagramSocket socket;
    private List<Room> rooms;
    private Map<String, User> existingUsers;
    private LogicMapHandler logicMapHandler;
    private Thread senderThread;
    private UdpSender sender;
    public UdpServer() throws IOException {
        this.socket = new DatagramSocket(PORT);
        this.existingUsers = ExistingUsers.getInstance();
        this.rooms = RoomList.getInstance();
        logicMapHandler = new LogicMapHandler();
        sender = new UdpSender(socket);
        senderThread = new Thread(sender);
        senderThread.start();
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
    }

    private void createBullet(String id, String content){
        User currentUser = existingUsers.get(id);
        if(currentUser.getPlayer().canPlayerShoot()){
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
    }

    private void createOpponent(){
        String opponentType = "Nietzsche";
        AOpponent newOpponent = OpponentFabric.createOpponent(opponentType);
        OpponentList.addOpponent(newOpponent);
        String message = "createOpponent:" + newOpponent.getType() + ":" + newOpponent.getId();
        for(User user : existingUsers.values()){
            if(user.getConnection())
                sendPackage(message, user.getAddress(), user.getUdpPort());
        }
        opponentType = "Schopenheuer";
        newOpponent = OpponentFabric.createOpponent(opponentType);
        OpponentList.addOpponent(newOpponent);
        message = "createOpponent:" + newOpponent.getType() + ":" + newOpponent.getId();
        for(User user : existingUsers.values()){
            if(user.getConnection())
                sendPackage(message, user.getAddress(), user.getUdpPort());
        }
    }

    private void initNewPlayer(String id, DatagramPacket packet){
        sendPackage("Server: Connected", packet.getAddress(), packet.getPort());

        for(User user : existingUsers.values()){
            sendPackage("newPlayer:player" + (existingUsers.size()-1), user.getAddress(), user.getUdpPort());
        }

        for (User current : existingUsers.values()) {
            String message = "init:"+current.getName() + ":" + current.getPlayer().getX() + ":" + current.getPlayer().getY();
            if(current.getConnection())
                sendPackage(message, packet.getAddress(), packet.getPort());
        }
        createOpponent();
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
        //System.out.println(id + ":" + content);
        String[] splitedArray = content.split(":");
        switch (splitedArray[0]){
            case "greetings": initNewPlayer(id, packet); break;
            case "createBullet": createBullet(id, content); break;
            default: updatePlayerPosition(id, content); break;
        }
    }
}
