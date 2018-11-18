import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class TcpClientThread extends Thread{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Map<String, User> existingUsers;
    public User user;

    public TcpClientThread(Socket clientSocket) {
        this.existingUsers = ExistingUsers.getInstance();
        this.clientSocket = clientSocket;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        System.out.println ("New Communication Thread Started " + clientSocket.getPort());
        String message;
        try {
            while ((message = in.readLine()) != null) {
                readMessage(message);
            }
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            existingUsers.get(user.getId()).setConnection(false);
            System.out.println("Rozłączono");
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public void readMessage(String message){
        String[] splitedArray = message.split(":");
        switch (splitedArray[0]) {
            case "udpPort": newPlayer(splitedArray[1]); break;
        }

        System.out.println("echo: " + message);
    }

    public void newPlayer(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        this.user = new User(clientSocket.getInetAddress(),clientSocket.getPort(),Integer.valueOf(udpPort), id, "player"+ existingUsers.size());
        existingUsers.put(id, this.user);
    }
}
