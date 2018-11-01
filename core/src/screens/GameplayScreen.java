package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.tiled.TiledMap;

import com.my.game.Connection;
import com.my.game.Player.Player;
import com.my.game.WBGame;

public class GameplayScreen extends AbstractScreen{

    private Texture playerTexture;
    private Player player;
    private Array<Player> players;
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;
    public Connection connection;

    @Override
    public void show(){
        map = new TmxMapLoader().load("maps/map1.tmx");
        render = new OrthogonalTiledMapRenderer(map);

    }

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
        camera.translate(960,600);
        player = new Player(playerTexture, true);
        player.setPosition(500,500);
        players = new Array<Player>();
    }

    @Override
    public void render(float delta) {

        super.render(delta);
        update();

        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        render.setView(camera);
        render.render();
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);
        System.out.println(screenShiftX);
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
        int numerOfXTiles = map.getProperties().get("width", Integer.class);
        int numerOfYTiles = map.getProperties().get("height", Integer.class);
        int mapWidth = numerOfXTiles * 32;
        int mapHeight = numerOfYTiles * 32;
        int shiftX = (int)(300 * Gdx.graphics.getDeltaTime());
        int shiftY = (int)(300 * Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            try {
                this.connection.s.sendMessage("A");
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.setX(player.getX() - shiftX);
            screenShiftX -= shiftX;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            try {
                this.connection.s.sendMessage("D");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.setX(player.getX() + shiftX);
            screenShiftX += shiftX;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            try {
                this.connection.s.sendMessage("W");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.setY(player.getY() + shiftY);
            screenShiftY += shiftY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            try {
                this.connection.s.sendMessage("S");
            } catch (Exception e) {
                e.printStackTrace();
            }

            player.setY(player.getY() - shiftY);
            screenShiftY -= shiftY;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            //TODO send message about player has left the game
            Gdx.app.exit();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            //TODO Toggle fullscreen
        }
        if(screenShiftX > 1300 && screenShiftX < mapWidth - 400){
            camera.translate(shiftX,0);
            screenShiftX -= shiftX;

        }
        if(screenShiftX < 300 && screenShiftX > 0){
            camera.translate(-shiftX,0);
            screenShiftX += shiftX;

        }
        if(screenShiftY < 200){
            camera.translate(0,-shiftY);
            screenShiftY += shiftY;

        }
        if(screenShiftY > 700){
            camera.translate(0,shiftY);
            screenShiftY -= shiftY;
        }
        camera.update();
    }
}
