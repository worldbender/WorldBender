package com.my.game.connection;

import com.my.game.bullets.BulletList;
import com.my.game.opponents.OpponentList;
import com.my.game.player.Player;
import com.my.game.player.PlayerList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

public class UdpPacketReceiver implements Runnable {
    private DatagramSocket socket;
    private byte buf[];
    private static Map<String, Player> players;

    UdpPacketReceiver(DatagramSocket socket) {
        this.socket = socket;
        buf = new byte[4096];
        players = PlayerList.getInstance();
    }

    public static Map<String, Player> getPlayers() {
        return players;
    }

    private void setPlayersData(JSONArray received) {
        for (Player player : players.values()) {
            for(int i = 0 ; i < received.length() ; i++){
                JSONObject receivedPlayer = received.getJSONObject(i);
                if (player.getName().equals(receivedPlayer.getString("name"))) {
                    player.setPosition(receivedPlayer.getInt("x"), receivedPlayer.getInt("y"));
                    player.setHp(receivedPlayer.getInt("hp"));
                    player.setActiveMovementKey(receivedPlayer.getString("activeMovementKey"));
                    player.setHeadDirection(receivedPlayer.getString("headDirection"));
                    player.setMoving(receivedPlayer.getBoolean("isMoving"));
                }
            }
        }
    }
    private void setBulletsPositions(JSONArray bullets){
        for(int i = 0 ; i < bullets.length() ; i++){
            JSONObject receivedBullet = bullets.getJSONObject(i);
            int bulletId = receivedBullet.getInt("id");
            double bulletX = receivedBullet.getInt("x");
            double bulletY = receivedBullet.getInt("y");
            BulletList.setBulletPosition(bulletId, (int)bulletX, (int)bulletY);
        }
    }
    private void setOpponentsData(JSONArray opponents){
        for(int i = 0 ; i < opponents.length() ; i++){
            JSONObject receivedOpponent = opponents.getJSONObject(i);
            int opponentId = receivedOpponent.getInt("id");
            int opponentX = receivedOpponent.getInt("x");
            int opponentY = receivedOpponent.getInt("y");
            int opponentHp = receivedOpponent.getInt("hp");
            OpponentList.setOpponentData(opponentId, opponentX, opponentY, opponentHp);
        }
    }

    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                JSONObject json = new JSONObject(received);

                switch (json.getString("msg")) {
                    case "game":
                        JSONObject gameJson = (JSONObject) json.get("content");
                        setPlayersData(gameJson.getJSONArray("players"));
                        setBulletsPositions(gameJson.getJSONArray("bullets"));
                        setOpponentsData(gameJson.getJSONArray("opponents")); break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
