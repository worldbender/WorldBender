package com.my.game.Connection;

import com.my.game.Player.Player;
import com.my.game.Player.PlayerList;
import java.io.*;
import java.net.Socket;
import java.util.Map;

public class TCPConnection extends Thread {
    private final int PORT = 10008;
    private String serverHostname = "localhost";
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static Map<String, Player> players;

    public TCPConnection() throws IOException {
        socket = new Socket(serverHostname, PORT);
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
        switch (splitedArray[0]) {
            case "dc": players.remove(splitedArray[1]); break;
        }
        System.out.println("echo: " + message);
    }

}
