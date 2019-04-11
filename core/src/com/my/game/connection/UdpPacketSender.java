package com.my.game.connection;

import com.my.game.Properties;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UdpPacketSender implements Runnable {
    private static final int PORT = Integer.parseInt(Properties.loadConfigFile("PORT_UDP"));
    private DatagramSocket socket;
    private String hostname;
    byte buf[];

    UdpPacketSender(DatagramSocket socket, String hostname) {
        this.socket = socket;
        this.hostname = hostname;
    }

    public void sendMessage(JSONObject s) {
        buf = s.toString().getBytes();
        InetAddress address;
        try {
            address = InetAddress.getByName(hostname);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
            socket.send(packet);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
    }
}