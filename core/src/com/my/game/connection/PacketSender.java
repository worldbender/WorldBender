package com.my.game.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PacketSender implements Runnable {
    private final static int PORT = 7331;
    private DatagramSocket socket;
    private String hostname;

    PacketSender(DatagramSocket socket, String hostname) {
        this.socket = socket;
        this.hostname = hostname;
    }

    public void sendMessage(String s) {
        byte buf[] = s.getBytes();
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
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                while (!in.ready()) {
                    Thread.sleep(100);
                }
                sendMessage(in.readLine());
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        }
    }
}