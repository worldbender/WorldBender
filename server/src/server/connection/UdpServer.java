package server.connection;

import server.rooms.Room;
import server.rooms.RoomList;
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

    private void createBullet(String id){
        User currentUser = existingUsers.get(id);
        if(currentUser.getPlayer().canPlayerShoot()){
            currentUser.getPlayer().shoot();
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
            case "createBullet": createBullet(id); break;
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
