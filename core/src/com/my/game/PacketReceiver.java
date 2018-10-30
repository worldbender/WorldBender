package com.my.game;

import com.badlogic.gdx.utils.Array;
import com.my.game.Player.Player;

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
        Player p = new Player(splitedArray[1], splitedArray[2], splitedArray[3]);
        players.add(p);
    }

    private void setPlayersPositions(String received) {
        String[] splitedArray = received.split(":");
        for (Player d : players) {
            if (d.getName().equals(splitedArray[1])) {
                d.setPosition(Integer.parseInt(splitedArray[2]), Integer.parseInt(splitedArray[3]));
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
                String[] splitedArray = received.split(":");

                if (splitedArray[0].equals("newPlayer")) {
                    newPlayer(splitedArray[1]);
                }
                if (splitedArray[0].equals("init")) {
                    setPlayers(received);
                }
                if (splitedArray[0].equals("updatePosition")) {
                    setPlayersPositions(received);
                }
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        }
    }
}
