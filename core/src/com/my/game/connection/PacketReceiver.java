package com.my.game.connection;

import com.my.game.bullets.ABullet;
import com.my.game.bullets.BulletFabric;
import com.my.game.bullets.BulletList;
import com.my.game.opponents.AOpponent;
import com.my.game.opponents.OpponentFabric;
import com.my.game.opponents.OpponentList;
import com.my.game.player.Player;
import com.my.game.player.PlayerList;
import screens.GameplayScreen;

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
        p.setCurrentPlayer(true);
        GameplayScreen.currentPlayer = p;
        players.put(splitedArray[1],p);
    }

    private void setPlayersData(String received) {
        String[] splitedArray = received.split(":");
        for (Player player : players.values()) {
            if (player.getName().equals(splitedArray[1])) {
                player.setPosition(Integer.parseInt(splitedArray[2]), Integer.parseInt(splitedArray[3]));
                player.setHp(Integer.parseInt(splitedArray[4]));
                player.setActiveMovementKey(splitedArray[5]);
                player.setMoving(Boolean.parseBoolean(splitedArray[6]));
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
    private void setOpponentsData(String date){
        String[] splitedDate = date.split(":");
        int opponentId = Integer.parseInt(splitedDate[1]);
        int opponentX = Integer.parseInt(splitedDate[2]);
        int opponentY = Integer.parseInt(splitedDate[3]);
        int opponentHp = Integer.parseInt(splitedDate[4]);
        OpponentList.setOpponentData(opponentId, opponentX, opponentY, opponentHp);
    }

    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //System.out.println(received);
                String[] splitedArray = received.split(":");

                switch (splitedArray[0]) {
                    case "newPlayer":
                        newPlayer(splitedArray[1]); break;
                    case "init":
                        setPlayers(received); break;
                    case "updatePosition":
                        setPlayersData(received); break;
                    case "updateBulletPosition":
                        setBulletsPositions(received); break;
                    case "createBullet":
                        ABullet newBullet = BulletFabric.createBullet(splitedArray[1],Integer.parseInt(splitedArray[2]), Float.parseFloat(splitedArray[3]));
                        BulletList.addBullet(newBullet); break;
                    case "updateOpponentData":
                        setOpponentsData(received); break;
                    case "createOpponent":
                        AOpponent newOpponent = OpponentFabric.createOpponent(splitedArray[1],Integer.parseInt(splitedArray[2]));
                        OpponentList.addOpponent(newOpponent); break;
                    case "deleteBullet":
                        BulletList.removeBulletById(Integer.parseInt(splitedArray[1])); break;
                    case "deleteOpponent":
                        OpponentList.removeOpponentById(Integer.parseInt(splitedArray[1])); break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
