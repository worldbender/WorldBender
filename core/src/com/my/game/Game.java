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

    PacketReceiver r;
    PacketSender s;
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
        this.r = new PacketReceiver(socket);
        this.s = new PacketSender(socket, host);
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