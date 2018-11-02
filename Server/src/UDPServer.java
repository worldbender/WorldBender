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

                System.out.println(id + ":" + content);

                if(!existingUsers.containsKey(id)){
                    initNewPlayer(id, packet);
                }
                else{
                    String[] splitedArray = content.split(":");
                    switch (splitedArray[0]) {
                        //case "updatePosition": break;
                        default:
                            updatePlayerPosition(id, content);


                    }
                }

            } catch(Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        }
    }

    private void updatePlayerPosition(String id, String content){
        User currentUser = existingUsers.get(id);
        currentUser.player.setPosition(content);
        String message = "updatePosition:"+currentUser.getName() + ":" + currentUser.player.getX()+ ":"+ currentUser.player.getY();

        for (User current : existingUsers.values()) {
            sendPackage(message, current.getAddress(), current.getPort());
        }
    }

    private void initNewPlayer(String id, DatagramPacket packet){

        sendPackage("Server: Connected", packet.getAddress(), packet.getPort());
        User user2 = new User(packet.getAddress(), packet.getPort(), id, "player"+ existingUsers.size());
        existingUsers.put(id, user2);

        for(User user : existingUsers.values()){
            sendPackage("newPlayer:player" + existingUsers.size(), user.getAddress(), user.getPort());
        }

        for (User current : existingUsers.values()) {
            String message = "init:"+current.getName() + ":" + current.player.getX() + ":" + current.player.getY();

            sendPackage(message, packet.getAddress(), packet.getPort());
        }
    }

    private void sendPackage(String message, InetAddress clientAddress, int clientPort){
        DatagramPacket packet;
        byte[] data = message.getBytes();
        packet = new DatagramPacket(data, data.length, clientAddress, clientPort);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        UDPServer server = new UDPServer();
        server.start();
    }
}