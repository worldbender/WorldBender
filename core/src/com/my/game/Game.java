package com.my.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.Callable;

public class Game extends ApplicationAdapter {

    private Texture playerTexture;
    private Player player;
    private Array<Player> players;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    BufferedReader inFromUser;
    DatagramSocket clientSocket;
    InetAddress IPAddress;

    MessageReceiver r;
    MessageSender s;
    Thread rt;
    Thread st;

    public Game() {
        String host = "localhost";
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.r = new MessageReceiver(socket);
        this.s = new MessageSender(socket, host);
        this.rt = new Thread(r);
        this.st = new Thread(s);
        rt.start();
        st.start();
    }

    @Override
    public void create() {
        this.inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
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


        loadData();
        init();
    }

    private void loadData() {
        playerTexture = new Texture("cat.png");
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(480, 600);

        player = new Player(playerTexture, true);
        players = new Array<Player>();
    }

    @Override
    public void render() {
        update();

        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        player.draw(batch);

        for (int i=0; i < players.size; i++) {
            players.get(i).texture = playerTexture;
            players.get(i).draw(batch);
        }

        batch.end();
    }

    private void update() {
        handleInput();
        //System.out.println("Update");
        Array<Player> p = this.r.getPlayers();
        if(players.size<p.size){
            //System.out.println("New player!");
            players = p;
        }
        //camera.update();
        //camera.position.set(player.x + player.width / 2,
        //        player.y + 300, 0);

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.A)) {
            try {
                this.s.sendMessage("A");
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.x -= 300 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            try {
                this.s.sendMessage("D");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.x += 300 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            try {
                this.s.sendMessage("W");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.y += 300 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            try {
                this.s.sendMessage("S");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.y -= 300 * Gdx.graphics.getDeltaTime();
        }
    }

}

class MessageSender implements Runnable {
    public final static int PORT = 7331;
    private DatagramSocket sock;
    private String hostname;

    MessageSender(DatagramSocket s, String h) {
        sock = s;
        hostname = h;
    }

    public void sendMessage(String s) throws Exception {
        byte buf[] = s.getBytes();
        InetAddress address = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        sock.send(packet);
    }


    public void run() {
        boolean connected = false;
        do {
            try {
                sendMessage("GREETINGS");
                connected = true;
            } catch (Exception e) {

            }
        } while (!connected);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                while (!in.ready()) {
                    Thread.sleep(100);
                }
                sendMessage(in.readLine());
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}

class MessageReceiver implements Runnable {
    DatagramSocket sock;
    byte buf[];
    Array<Player> players = new Array<Player>();

    MessageReceiver(DatagramSocket s) {
        sock = s;
        buf = new byte[1024];
    }

    public void newPlayer(String name){
        Player p = new Player(name, 0, 0);
        players.add(p);
    }

    public Array<Player> getPlayers(){
        /*for(Player p : players){
            System.out.println(p);
        }*/
        return players;
    }

    public void setPlayers(String received){
        String[] splitedArray = received.split(":");
        Player p = new Player(splitedArray[0],splitedArray[1], splitedArray[2]);
        players.add(p);
    }

    public void setPlayersPositions(String received){
        String[] splitedArray = received.split(":");
        for(Player d : players){
            if(d.getName().equals(splitedArray[0])){
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
