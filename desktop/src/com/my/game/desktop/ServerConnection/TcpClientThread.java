package com.my.game.desktop.ServerConnection;


import com.my.game.desktop.ExistingUsers;
import com.my.game.desktop.Room;
import com.my.game.desktop.RoomList;
import com.my.game.desktop.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class TcpClientThread extends Thread{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Map<String, User> existingUsers;
    private List<Room> rooms;
    public User user;

    public TcpClientThread(Socket clientSocket) {
        this.user = new User();
        this.existingUsers = ExistingUsers.getInstance();
        this.rooms = RoomList.getInstance();
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
            existingUsers.get(user.getConnectionId()).setConnection(false);
            for (User current : existingUsers.values()) {
                current.getThread().sendMessage("dc:"+user.getName());
            }
            System.out.println("Rozłączono");
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public void readMessage(String message){
        String[] splitedArray = message.split(":");
        if ("udpPort".equals(splitedArray[0])) {
            newPlayer(splitedArray[1]);
        }

        System.out.println("echo: " + message);
    }

    public void newPlayer(String udpPort){
        String id = clientSocket.getInetAddress().toString() + "," + udpPort;
        this.user.setAddress(clientSocket.getInetAddress());
        this.user.setTcpPort(clientSocket.getPort());
        this.user.setUdpPort(Integer.valueOf(udpPort));
        this.user.setConnectionId(id);
        this.user.setName("player"+ existingUsers.size());
        existingUsers.put(id, this.user);
        existingUsers.get(user.getConnectionId()).setThread(this);
    }
}
