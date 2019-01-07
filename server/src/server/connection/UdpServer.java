package server.connection;

import server.bullets.ABullet;
import server.bullets.BulletFabric;
import server.bullets.BulletList;
import server.*;

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
    private GameController sender;
    public UdpServer() throws IOException {
        this.socket = new DatagramSocket(PORT);
        this.existingUsers = ExistingUsers.getInstance();
        this.rooms = RoomList.getInstance();
        logicMapHandler = new LogicMapHandler();
        sender = new GameController(socket);
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

    private void updatePlayersState(String id, String content){
        User currentUser = existingUsers.get(id);
        String splitedContetnt[] = content.split(":");
        boolean isMoving = Boolean.parseBoolean(splitedContetnt[0]);
        currentUser.getPlayer().setMoving(isMoving);
    }

    private void createBullet(String id, String content){
        String[] splitedContent = content.split(":");
        User currentUser = existingUsers.get(id);
        currentUser.getPlayer().setActiveMovementKeyByAngle(splitedContent[2]);
        currentUser.getPlayer().setMoving(true);
        if(currentUser.getPlayer().canPlayerShoot()){
            String bulletType = splitedContent[1];
            String angle = splitedContent[2];
            ABullet newBullet = BulletFabric.createBullet(bulletType, currentUser.getPlayer().getCenterX(), currentUser.getPlayer().getCenterY(), Float.parseFloat(angle));
            BulletList.addBullet(newBullet);
            String message = "createBullet:" + newBullet.getType() + ":" + newBullet.getId() + ":" + newBullet.getAngle();
            for(User user : existingUsers.values()){
                if(user.hasConnection())
                    sendPackage(message, user.getAddress(), user.getUdpPort());
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

    private void readPackage(DatagramPacket packet, byte[] buf){
        String content = new String(buf);
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();
        String id = clientAddress.toString() + "," + clientPort;
        //System.out.println(id + ":" + content);
        String[] splitedArray = content.split(":");
        switch (splitedArray[0]){
            case "createBullet": createBullet(id, content); break;
            case "playerState": updatePlayersState(id, content); break;
            default: updatePlayerPosition(id, content); break;
        }
    }
}
