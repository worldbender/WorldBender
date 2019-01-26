package com.my.game.connection;

import com.badlogic.gdx.Gdx;
import com.my.game.WBGame;
import com.my.game.opponents.AOpponent;
import com.my.game.opponents.OpponentFabric;
import com.my.game.opponents.OpponentList;
import com.my.game.player.Player;
import com.my.game.player.PlayerList;
import com.my.game.Properties;
import com.my.game.screens.GameplayScreen;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class TCPConnection extends Thread {
    private WBGame game;
    private final int PORT = Integer.parseInt(Properties.loadConfigFile("PortTcp"));
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
                if((message=in.readLine())!=null)
                    readMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }

    public void readMessage(String message){
        String[] splitedArray = message.split(":");
        switch(splitedArray[0]) {
        case "dc":
            players.remove(splitedArray[1]);
            System.out.println("Remove player: " + splitedArray[1]);
            break;
        case "init":
            System.out.println("Init player: " + splitedArray[1]);
            Player p = new Player(splitedArray[1], splitedArray[2], splitedArray[3]);
            if(splitedArray[4].equals("true")){
                p.setCurrentPlayer(true);
                GameplayScreen.currentPlayer = p;
            }
            players.put(splitedArray[1], p);
            break;
        case "newPlayer":
            System.out.println("New player connected: " + splitedArray[1]);
            newPlayer(splitedArray[1]);
            break;
        case "createOpponent":
            AOpponent newOpponent = OpponentFabric.createOpponent(splitedArray[1], Integer.parseInt(splitedArray[2]));
            OpponentList.addOpponent(newOpponent);
            break;
        case "deleteOpponent":
            OpponentList.removeOpponentById(Integer.parseInt(splitedArray[1]));
            break;
        case "startGame":
            System.out.println("Game started by: " + splitedArray[1]);
            Gdx.app.postRunnable(() -> game.changeScreen(WBGame.PLAY));
            break;
        case "joinedRoom":
            System.out.println("Joined room successfully: " + splitedArray[1]);
            Gdx.app.postRunnable(() -> game.changeScreen(WBGame.ROOM));
            break;
        case "fullRoom":
            System.out.println("Room is full: " + splitedArray[1]);
            Gdx.app.postRunnable(() -> game.changeScreen(WBGame.MENU_FULL_ROOM));
            break;
        }
    }

    private void newPlayer(String name) {
        Player p = new Player(name, 0, 0);
        players.put(name,p);
    }
}
