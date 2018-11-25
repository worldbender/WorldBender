package com.my.game.Connection;

import com.my.game.WBGame;

import java.io.IOException;
import java.net.*;

public class Connection {
    String host = WBGame.SERVER_ADRESS;
    DatagramSocket socket;
    InetAddress IPAddress;
    public TCPConnection tcp;

    public PacketReceiver receiver;
    public PacketSender sender;
    Thread receiverThread;
    Thread senderThread;
    Thread tcpThread;

    public Connection(){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            IPAddress = InetAddress.getByName(WBGame.SERVER_ADRESS);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void createConnection() {
        try {
            tcp = new TCPConnection();
            tcpThread = new Thread(tcp);
            tcpThread.start();
            tcp.sendMessage(String.valueOf("udpPort:"+socket.getLocalPort()));
            receiver = new PacketReceiver(socket);
            sender = new PacketSender(socket, host);
            receiverThread = new Thread(receiver);
            senderThread = new Thread(sender);
            receiverThread.start();
            senderThread.start();
            sender.sendMessage("greetings:me");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
