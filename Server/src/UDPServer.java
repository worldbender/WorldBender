import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Map;

public class UDPServer extends Thread {
    private final static int PORT = 7331;
    private final static int BUFFER = 1024;
    private DatagramSocket socket;
    //private ArrayList<Room> rooms;
    private Map<String, User> existingUsers;

    public UDPServer() throws IOException {
        socket = new DatagramSocket(PORT);

        this.existingUsers = ExistingUsers.getInstance();
        //rooms = new ArrayList();
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

                String[] splitedArray = content.split(":");
                switch (splitedArray[0]) {
                    case "greetings": initNewPlayer(id, packet); break;
                    default: updatePlayerPosition(id, content);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePlayerPosition(String id, String content){
        User currentUser = existingUsers.get(id);
        currentUser.player.setPosition(content);
        String message = "updatePosition:"+currentUser.getName() + ":" + currentUser.player.getX()+ ":"+ currentUser.player.getY();

        for (User current : existingUsers.values()) {
            if(current.getConnection())
                sendPackage(message, current.getAddress(), current.getUdpPort());
        }
    }

    private void updatePlayerPosition(String id){
        User currentUser = existingUsers.get(id);
        String message = "updatePosition:"+currentUser.getName() + ":" + currentUser.player.getX()+ ":"+ currentUser.player.getY();

        for (User current : existingUsers.values()) {
            if(current.getConnection())
                sendPackage(message, current.getAddress(), current.getUdpPort());
        }
    }

    private void initNewPlayer(String id, DatagramPacket packet){

        sendPackage("Server: Connected", packet.getAddress(), packet.getPort());
        /*User user2 = new User(packet.getAddress(), packet.getPort(), id, "player"+ existingUsers.size());
        existingUsers.put(id, user2);*/

        for(User user : existingUsers.values()){
            sendPackage("newPlayer:player" + (existingUsers.size()-1), user.getAddress(), user.getUdpPort());
            updatePlayerPosition(id);
        }

        for (User current : existingUsers.values()) {
            String message = "init:"+current.getName() + ":" + current.player.getX() + ":" + current.player.getY();
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
}
