package com.my.game.Connection;

import com.my.game.Player.Player;
import com.my.game.Player.PlayerList;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;


public class PacketReceiver implements Runnable {
    private DatagramSocket socket;
    private byte buf[];
    private static Map<String, Player> players;

    PacketReceiver(DatagramSocket socket) {
        this.socket = socket;
        buf = new byte[1024];
        players = PlayerList.getInstance();
    }

    private void newPlayer(String name) {
        Player p = new Player(name, 0, 0);
        players.put(name,p);
    }

    public static Map<String, Player> getPlayers() {
        return players;
    }

    private void setPlayers(String received) {
        String[] splitedArray = received.split(":");
        Player p = new Player(splitedArray[1], splitedArray[2], splitedArray[3]);

        players.put(splitedArray[1],p);
    }

    private void setPlayersPositions(String received) {
        String[] splitedArray = received.split(":");
        for (Player d : players.values()) {
            if (d.getName().equals(splitedArray[1])) {
                d.setPosition(Integer.parseInt(splitedArray[2]), Integer.parseInt(splitedArray[3]));
            }
        }
    }

    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
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
                e.printStackTrace();
            }
        }
    }
}
