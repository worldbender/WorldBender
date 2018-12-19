package com.my.game.connection;

import com.my.game.WBGame;

import java.io.IOException;
import java.net.*;

public class Connection {
    InetAddress IPAddress;
    private String hostName = WBGame.SERVER_ADRESS;
    private DatagramSocket socket;
    public TCPConnection tcp;
    public PacketReceiver receiver;
    public PacketSender sender;
    private Thread receiverThread;
    private Thread senderThread;
    private Thread tcpThread;

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

    public void createConnection() throws IOException {
        tcp = new TCPConnection(hostName);
        tcpThread = new Thread(tcp);
        tcpThread.start();
        tcp.sendMessage(String.valueOf("udpPort:" + socket.getLocalPort()));
        receiver = new PacketReceiver(socket);
        sender = new PacketSender(socket, hostName);
        receiverThread = new Thread(receiver);
        senderThread = new Thread(sender);
        receiverThread.start();
        senderThread.start();
        sender.sendMessage("greetings:me");
    }
}
