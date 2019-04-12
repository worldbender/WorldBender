package com.my.game.connection;

import com.my.game.WBGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Connection {
    private static Logger logger = LogManager.getLogger(Connection.class.getName());
    private String hostName = WBGame.SERVER_ADDRESS;
    private DatagramSocket socket;
    private TCPConnection tcp;
    private UdpPacketReceiver receiver;
    private UdpPacketSender sender;
    private Thread receiverThread;
    private Thread senderThread;
    private Thread tcpThread;
    private WBGame game;

    public Connection(WBGame game) {
        this.game = game;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            logger.error(e.toString(), e);
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

    public DatagramSocket getSocket(){
        return this.socket;
    }

    public TCPConnection getTcp(){
        return this.tcp;
    }

    public UdpPacketSender getSender(){
        return this.sender;
    }
}
