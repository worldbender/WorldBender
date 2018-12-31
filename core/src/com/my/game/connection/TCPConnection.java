package com.my.game.connection;

import com.my.game.player.Player;
import com.my.game.player.PlayerList;
import com.my.game.Properties;
import java.io.*;
import java.net.Socket;
import java.util.Map;

public class TCPConnection extends Thread {
    private final int PORT = Integer.parseInt(Properties.loadConfigFile("PortTcp"));
    private String hostname;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static Map<String, Player> players;

    public TCPConnection(String hostName) throws IOException {
        this.hostname = hostName;
        socket = new Socket(hostname, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        players = PlayerList.getInstance();
    }

    public void run() {
        String message;
        while (true) {
            try {
                if((message=in.readLine())!=null)
                    readMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public void readMessage(String message){
        String[] splitedArray = message.split(":");
        if ("dc".equals(splitedArray[0])) {
            players.remove(splitedArray[1]);
        }
        System.out.println("remove player: " + message);
    }

}
