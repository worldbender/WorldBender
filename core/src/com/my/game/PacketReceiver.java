package com.my.game;

import com.badlogic.gdx.utils.Array;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class PacketReceiver implements Runnable {
    private DatagramSocket sock;
    private byte buf[];
    private Array<Player> players = new Array<Player>();

    PacketReceiver(DatagramSocket s) {
        sock = s;
        buf = new byte[1024];
    }

    private void newPlayer(String name) {
        Player p = new Player(name, 0, 0);
        players.add(p);
    }

    public Array<Player> getPlayers() {
        return players;
    }

    private void setPlayers(String received) {
        String[] splitedArray = received.split(":");
        Player p = new Player(splitedArray[0], splitedArray[1], splitedArray[2]);
        players.add(p);
    }

    private void setPlayersPositions(String received) {
        String[] splitedArray = received.split(":");
        for (Player d : players) {
            if (d.getName().equals(splitedArray[0])) {
                d.setPosition(Float.parseFloat(splitedArray[1]), Float.parseFloat(splitedArray[2]));
            }
        }
    }

    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                sock.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);
                if (received.substring(0, 10).equals("New player")) {
                    String[] splitedArray = received.split(":");
                    newPlayer(splitedArray[1]);
                }
                if (received.substring(0, 7).equals("Nplayer")) {
                    setPlayers(received);
                }
                if (received.substring(0, 6).equals("player")) {
                    setPlayersPositions(received);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
