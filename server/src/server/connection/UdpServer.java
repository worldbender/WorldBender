package server.connection;

import RoomsController.Room;
import RoomsController.RoomList;
import server.LogicMap.LogicMapHandler;
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
import java.util.concurrent.CopyOnWriteArrayList;

public class UdpServer extends Thread {
    private final static int PORT = Integer.parseInt(Properties.loadConfigFile("PortUdp"));
    private final static int BUFFER = 1024;
    private static DatagramSocket socket;
    private List<Room> rooms;
    private Map<String, User> existingUsers;

    public UdpServer() throws IOException {
        this.socket = new DatagramSocket(PORT);
        this.existingUsers = ExistingUsers.getInstance();
        this.rooms = RoomList.getInstance();
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

    private void updatePlayersState(String id, String content){
        User currentUser = existingUsers.get(id);
        String splitedContetnt[] = content.split(":");
        boolean isMoving = Boolean.parseBoolean(splitedContetnt[1]);
        String direction = splitedContetnt[2];
        currentUser.getPlayer().setMoving(isMoving);
        currentUser.getPlayer().setWSAD(splitedContetnt[3]);
        currentUser.getPlayer().setActiveMovementKey(direction);
    }

    private void createBullet(String id, String content){
        String[] splitedContent = content.split(":");
        User currentUser = existingUsers.get(id);
        Room currentRoom = RoomList.getUserRoom(id);
        currentUser.getPlayer().setActiveMovementKeyByAngle(splitedContent[2]);
        if(currentUser.getPlayer().canPlayerShoot()){
            String bulletType = splitedContent[1];
            String angle = splitedContent[2];
            ABullet newBullet = BulletFabric.createBullet(bulletType, currentUser.getPlayer().getCenterX(), currentUser.getPlayer().getCenterY(), Float.parseFloat(angle), false);
            currentRoom.bulletList.addBullet(newBullet);
            currentRoom.bulletList.addBulletsToCreateList(newBullet);
        }
    }
    public static void sendUdpMsgToAllUsers(String msg, Map<String, User> existingUsers){
        for(User user : existingUsers.values()){
            createPackage(msg, user);
        }
    }

    public static void sendUdpMsgToAllUsersInRoom(String msg, CopyOnWriteArrayList<User> usersInRoom){
        for(User user : usersInRoom){
            createPackage(msg, user);
        }
    }

    private void readPackage(DatagramPacket packet, byte[] buf){
        String content = new String(buf);
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();
        String id = clientAddress.toString() + "," + clientPort;
        String[] splitedArray = content.split(":");
        switch (splitedArray[0]){
            case "createBullet": createBullet(id, content); break;
            case "playerState": updatePlayersState(id, content); break;
        }
    }

    private static void createPackage(String msg, User user){
        if(user.hasConnection()){
            DatagramPacket packet;
            byte[] data = msg.getBytes();
            packet = new DatagramPacket(data, data.length, user.getAddress(), user.getUdpPort());
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
