package com.my.game.connection;

import com.my.game.WBGame;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection {
    public InetAddress IPAddress;
    private String hostName = WBGame.SERVER_ADDRESS;
    public DatagramSocket socket;
    public TCPConnection tcp;
    public UdpPacketReceiver receiver;
    public UdpPacketSender sender;
    private Thread receiverThread;
    private Thread senderThread;
    private Thread tcpThread;
    private WBGame game;

    public Connection(WBGame game) {
        this.game = game;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            IPAddress = InetAddress.getByName(WBGame.SERVER_ADDRESS);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void createConnection() throws IOException {
        initTcp();
        initUdp();
    }

    private void initTcp() throws IOException {
        tcp = new TCPConnection(hostName, game);
        tcpThread = new Thread(tcp);
        tcpThread.start();

        tcp.sendMessage(
                new JSONObject()
                        .put("msg", "udpPort")
                        .put("content", new JSONObject().put("port", socket.getLocalPort()))
        );
    }


    private void initUdp() {
        receiver = new UdpPacketReceiver(socket);
        sender = new UdpPacketSender(socket, hostName);
        receiverThread = new Thread(receiver);
        senderThread = new Thread(sender);
        receiverThread.start();
        senderThread.start();
    }
}
