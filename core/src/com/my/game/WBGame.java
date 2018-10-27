package com.my.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class WBGame extends Game {

    public final static String GAME_NAME = "World Bender";
    public final static int WIDTH = 480;
    public final static int HEIGHT = 600;

    private Texture playerTexture;
    private Player player;
    private Array<Player> players;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    Connection connection;

    public WBGame() {
        connection = new Connection();
    }

    @Override
    public void create() {
        connection.createConnection();

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
        Array<Player> p = this.connection.r.getPlayers();
        if(players.size<p.size){
            players = p;
        }
        //camera.update();
        //camera.position.set(player.x + player.width / 2,
        //        player.y + 300, 0);

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.A)) {
            try {
                this.connection.s.sendMessage("A");
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.x -= 300 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            try {
                this.connection.s.sendMessage("D");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.x += 300 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            try {
                this.connection.s.sendMessage("W");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.y += 300 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            try {
                this.connection.s.sendMessage("S");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.y -= 300 * Gdx.graphics.getDeltaTime();
        }
    }
}