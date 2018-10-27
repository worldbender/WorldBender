package com.my.game;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection {
    DatagramSocket clientSocket;
    InetAddress IPAddress;

    public PacketReceiver r;
    public PacketSender s;
    Thread rt;
    Thread st;

    public Connection(){
        String host = "localhost";
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.r = new PacketReceiver(socket);
        this.s = new PacketSender(socket, host);
        this.rt = new Thread(r);
        this.st = new Thread(s);
        rt.start();
        st.start();
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
        }
    }
}
