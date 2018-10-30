package com.my.game;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection {
    DatagramSocket clientSocket;
    InetAddress IPAddress;

    public PacketReceiver receiver;
    public PacketSender sender;
    Thread receiverThread;
    Thread senderThread;

    public Connection(){
        String host = "localhost";
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.receiver = new PacketReceiver(socket);
        this.sender = new PacketSender(socket, host);
        this.receiverThread = new Thread(receiver);
        this.senderThread = new Thread(sender);
        receiverThread.start();
        senderThread.start();
    }

    public void createConnection() {
        this.clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.IPAddress = null;
        try {
            IPAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
}
