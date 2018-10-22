package com.my.game;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

class UDPServer extends Thread
{
    public final static int PORT = 7331;
    private final static int BUFFER = 1024;

    private DatagramSocket socket;
    //private ArrayList<InetAddress> clientAddresses;
    //private ArrayList<Integer> clientPorts;
    private ArrayList<Client> existingClients;
    private HashSet<String> existingClientIds;
    public UDPServer() throws IOException {
        socket = new DatagramSocket(PORT);
        //clientAddresses = new ArrayList();
        //clientPorts = new ArrayList();
        existingClients = new ArrayList();
        existingClientIds = new HashSet();
    }

    public void run() {
        byte[] buf = new byte[BUFFER];
        while (true) {
            try {
                Arrays.fill(buf, (byte)0);
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String content = new String(buf, buf.length);

                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                String id = clientAddress.toString() + "," + clientPort;
                Client currentClient = null;

                for(Client d : existingClients){
                    if(d.getAddress() == clientAddress && d.getPort()== clientPort){
                        currentClient = d;
                    }
                }

                if (!existingClientIds.contains(id)) {
                    for (int i=0; i < existingClients.size(); i++) {
                        Client current = existingClients.get(i);
                        InetAddress cl = current.getAddress();
                        int cp = current.getPort();
                        byte[] data = ("New player:player" + existingClients.size()).getBytes();
                        packet = new DatagramPacket(data, data.length, cl, cp);
                        socket.send(packet);
                    }


                    for (int i=0; i < existingClients.size(); i++) {
                        Client current = existingClients.get(i);
                        byte[] data2 = ("N"+current.getName() + ":" + current.getX() + ":" + current.getY()).getBytes();
                        packet = new DatagramPacket(data2, data2.length, clientAddress, clientPort);
                        socket.send(packet);
                    }

                    Client c = new Client(clientAddress, clientPort, id, "player"+existingClients.size());
                    existingClientIds.add( id );
                    existingClients.add( c );
                    //clientPorts.add( clientPort );
                    //clientAddresses.add(clientAddress);
                    byte[] data = ("Server: Connected").getBytes();
                    packet = new DatagramPacket(data, data.length, clientAddress, clientPort);
                    socket.send(packet);
                }

                System.out.println(id + ":" + content);


                for(Client d : existingClients){
                    if(d.getAddress().equals(clientAddress) && d.getPort()== clientPort){
                        currentClient = d;
                    }
                }
                currentClient.setPosition(content);
                byte[] data = (currentClient.getName() + ":" +currentClient.getX()+ ":"+currentClient.getY()).getBytes();

                for (int i=0; i < existingClients.size(); i++) {
                    Client current = existingClients.get(i);
                    InetAddress cl = current.getAddress();
                    int cp = current.getPort();
                    packet = new DatagramPacket(data, data.length, cl, cp);
                    socket.send(packet);
                }
            } catch(Exception e) {
                System.err.println(e);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        UDPServer s = new UDPServer();
        s.start();
    }
}