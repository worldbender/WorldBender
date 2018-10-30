import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class UDPServer extends Thread
{
    public final static int PORT = 7331;
    private final static int BUFFER = 1024;
    private DatagramSocket socket;
    private ArrayList<Room> rooms;
    private Map<String, User> existingUsers;

    public UDPServer() throws IOException {
        socket = new DatagramSocket(PORT);
        existingUsers = new HashMap<>();
        rooms = new ArrayList();
    }

    public void run() {
        byte[] buf = new byte[BUFFER];
        while (true) {
            try {
                Arrays.fill(buf, (byte)0);
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String content = new String(buf);

                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                String id = clientAddress.toString() + "," + clientPort;

                if(!existingUsers.containsKey(id)){
                    for(User user : existingUsers.values()){
                        InetAddress cl = user.getAddress();
                        int cp = user.getPort();
                        byte[] data = ("newPlayer:player" + existingUsers.size()).getBytes();
                        packet = new DatagramPacket(data, data.length, cl, cp);
                        socket.send(packet);
                }


                    for (User current : existingUsers.values()) {
                        byte[] data2 = ("init:"+current.getName() + ":" + current.player.getX() + ":" + current.player.getY()).getBytes();
                        packet = new DatagramPacket(data2, data2.length, clientAddress, clientPort);
                        socket.send(packet);
                    }

                    User user = new User(clientAddress, clientPort, id, "player"+ existingUsers.size());
                    existingUsers.put(id, user);
                    byte[] data = ("Server: Connected").getBytes();
                    packet = new DatagramPacket(data, data.length, clientAddress, clientPort);
                    socket.send(packet);
                }

                System.out.println(id + ":" + content);

                User currentUser = existingUsers.get(id);
                currentUser.player.setPosition(content);
                byte[] data = ("updatePosition:"+currentUser.getName() + ":" + currentUser.player.getX()+ ":"+ currentUser.player.getY()).getBytes();

                for (User current : existingUsers.values()) {
                    InetAddress cl = current.getAddress();
                    int cp = current.getPort();
                    packet = new DatagramPacket(data, data.length, cl, cp);
                    socket.send(packet);
                }

            } catch(Exception e) {
                System.err.println(e);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        UDPServer server = new UDPServer();
        server.start();
    }
}