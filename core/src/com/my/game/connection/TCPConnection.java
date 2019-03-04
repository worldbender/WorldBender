package com.my.game.connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.my.game.WBGame;
import com.my.game.bullets.ABullet;
import com.my.game.bullets.BulletFabric;
import com.my.game.bullets.BulletList;
import com.my.game.music.MusicPlayer;
import com.my.game.opponents.AOpponent;
import com.my.game.opponents.OpponentFabric;
import com.my.game.opponents.OpponentList;
import com.my.game.pickups.APickup;
import com.my.game.pickups.PickupFabric;
import com.my.game.pickups.PickupList;
import com.my.game.player.Player;
import com.my.game.player.PlayerList;
import com.my.game.Properties;
import com.my.game.screens.GameplayScreen;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class TCPConnection extends Thread {
    private WBGame game;
    private final int PORT = Integer.parseInt(Properties.loadConfigFile("PORT_TCP"));
    private String hostname;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static Map<String, Player> players;

    public TCPConnection(String hostName, WBGame game) throws IOException {
        this.game = game;
        this.hostname = hostName;
        socket = new Socket(hostname, PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        players = PlayerList.getInstance();
    }

    public void run() {
        String message;
        while (true) {
            try {
                if ((message = in.readLine()) != null){
                    readMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(JSONObject message) {
        out.println(message.toString());
    }

    public void readMessage(String message) {
        JSONObject obj = new JSONObject(message);
        JSONObject contentJSON = (JSONObject) obj.get("content");

        switch (obj.getString("msg")) {
            case "startGame":
                System.out.println(contentJSON);
                startGame(contentJSON);
                break;
            case "createBullet":
                ABullet newBullet = BulletFabric.createBullet(contentJSON.getString("type"),contentJSON.getInt("id"), contentJSON.getFloat("angle"));
                BulletList.addBullet(newBullet); break;
            case "deleteBullet":
                BulletList.removeBulletById(contentJSON.getInt("id")); break;
            case "dc":
                players.remove(contentJSON.get("name").toString());
                System.out.println("Remove player: " + (contentJSON.getString("name")));
                break;
            case "deleteOpponent":
                OpponentList.removeOpponentById(contentJSON.getInt("id"));
                break;
            case "createPickup":
                APickup pickup = PickupFabric.createPickup(
                        contentJSON.getInt("x"),
                        contentJSON.getInt("y"),
                        contentJSON.getInt("id"),
                        contentJSON.getString("type")
                );
                PickupList.addPickup(pickup);
                break;
            case "deletePickup":
                PickupList.removePickupById(contentJSON.getInt("id"));
                MusicPlayer.playHpUpSound();
                break;
            case "createdRoom":
                System.out.println("Created room ID: " + contentJSON.getInt("id"));
                Gdx.app.postRunnable(() -> game.changeScreen(WBGame.ROOM_OWNER, contentJSON.getInt("id")));
                break;
            case "joinedRoom":
                System.out.println("Joined room ID: " + contentJSON.getInt("id"));
                Gdx.app.postRunnable(() -> game.changeScreen(WBGame.ROOM, contentJSON.getInt("id")));
                break;
            case "fullRoom":
                System.out.println("Room is full: " + contentJSON.getInt("id"));
                Gdx.app.postRunnable(() -> game.changeScreen(WBGame.MENU_FULL_ROOM));
                break;
            case "roomDoesNotExist":
                System.out.println("Room doesn't exist: " + contentJSON.getInt("id"));
                Gdx.app.postRunnable(() -> game.changeScreen(WBGame.MENU_NO_ROOM));
                break;
            case "changeLevel":
                System.out.println(contentJSON);
                JSONArray opponents = contentJSON.getJSONArray("opponents");
                for (int i = 0; i < opponents.length(); i++) {
                    JSONObject opponent = opponents.getJSONObject(i);
                    AOpponent newOpponent = OpponentFabric.createOpponent(opponent.getString("type"), opponent.getInt("id"));
                    OpponentList.addOpponent(newOpponent);
                }
                game.getGameplayScreen().changeLevel(contentJSON.getString("map"));
                break;
        }
    }

    private void startGame(JSONObject contentJSON) {
        Gdx.app.postRunnable(() -> game.changeScreen(WBGame.PLAY));
        JSONArray opponents = contentJSON.getJSONArray("opponents");
        for (int i = 0; i < opponents.length(); i++) {
            JSONObject opponent = opponents.getJSONObject(i);
            AOpponent newOpponent = OpponentFabric.createOpponent(opponent.getString("type"), opponent.getInt("id"));
            OpponentList.addOpponent(newOpponent);
        }

        JSONArray players2 = contentJSON.getJSONArray("players");
        for (int i = 0; i < players2.length(); i++) {
            JSONObject player = players2.getJSONObject(i);
            Player p = new Player(player.getString("name"));
            if (player.getString("name").equals(contentJSON.getString("current"))) {
                p.setCurrentPlayer(true);
                GameplayScreen.currentPlayer = p;
            }
            players.put(player.getString("name"), p);
        }
    }
}
