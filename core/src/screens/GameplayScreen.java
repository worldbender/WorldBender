package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import com.my.game.Connection;
import com.my.game.Player;
import com.my.game.WBGame;

public class GameplayScreen extends AbstractScreen{

    private Texture playerTexture;
    private Player player;
    private Array<Player> players;

    public Connection connection;

    public GameplayScreen(WBGame game) {
        super(game);
        connection = new Connection();
        create();
    }

    public void create() {
        connection.createConnection();
        loadData();
        init();
    }

    private void loadData() {
        playerTexture = new Texture("cat.png");
    }

    private void init() {
        camera = new OrthographicCamera(WBGame.WIDTH, WBGame.HEIGHT);

        player = new Player(playerTexture, true);
        players = new Array<Player>();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update();

        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);

        player.draw(spriteBatch);

        for (int i=0; i < players.size; i++) {
            players.get(i).texture = playerTexture;
            players.get(i).draw(spriteBatch);
        }

        spriteBatch.end();
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
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            try {
                this.connection.s.sendMessage("A");
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.setX(player.getX() - (300 * Gdx.graphics.getDeltaTime()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            try {
                this.connection.s.sendMessage("D");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.setX(player.getX() + (300 * Gdx.graphics.getDeltaTime()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            try {
                this.connection.s.sendMessage("W");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.setY(player.getY() + (300 * Gdx.graphics.getDeltaTime()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            try {
                this.connection.s.sendMessage("S");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.setY(player.getY() - (300 * Gdx.graphics.getDeltaTime()));
        }
    }
}
