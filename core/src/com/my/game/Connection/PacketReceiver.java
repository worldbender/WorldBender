package com.my.game.Connection;

import com.my.game.CBullets.ABullet;
import com.my.game.CBullets.BulletFabric;
import com.my.game.CBullets.BulletList;
import com.my.game.COpponents.AOpponent;
import com.my.game.COpponents.OpponentFabric;
import com.my.game.COpponents.OpponentList;
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
    private void setBulletsPositions(String date){
        String[] splitedDate = date.split(":");
        int bulletId = Integer.parseInt(splitedDate[1]);
        int bulletX = Integer.parseInt(splitedDate[2]);
        int bulletY = Integer.parseInt(splitedDate[3]);
        BulletList.setBulletPosition(bulletId, bulletX, bulletY);
    }
    private void setOpponentsPosition(String date){
        String[] splitedDate = date.split(":");
        int opponentId = Integer.parseInt(splitedDate[1]);
        int opponentX = Integer.parseInt(splitedDate[2]);
        int opponentY = Integer.parseInt(splitedDate[3]);
        OpponentList.setOpponentPosition(opponentId, opponentX, opponentY);
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
                if (splitedArray[0].equals("updateBulletPosition")) {
                    setBulletsPositions(received);
                }
                if (splitedArray[0].equals("createBullet")) {
                    ABullet newBullet = BulletFabric.createBullet(splitedArray[1],Integer.parseInt(splitedArray[2]), Float.parseFloat(splitedArray[3]));
                    BulletList.addBullet(newBullet);
                }
                if (splitedArray[0].equals("updateOpponentPosition")) {
                    setOpponentsPosition(received);
                }
                if (splitedArray[0].equals("createOpponent")) {
                    AOpponent newOpponent = OpponentFabric.createOpponent(splitedArray[1],Integer.parseInt(splitedArray[2]));
                    OpponentList.addOpponent(newOpponent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
