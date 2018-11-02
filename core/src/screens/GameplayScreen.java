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
import com.my.game.music.MusicPlayer;

public class GameplayScreen extends AbstractScreen{

    private Texture playerTexture;
    private Array<Player> players;
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;
    public Connection connection;

    @Override
    public void show(){
        map = new TmxMapLoader().load("maps/map2.tmx");
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
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.playMusic();
    }

    private void loadData() {
        playerTexture = new Texture("cat.png");
    }

    private void init() {
        camera = new OrthographicCamera(WBGame.WIDTH, WBGame.HEIGHT);
        camera.translate(960,540);
        Player player = new Player(playerTexture, true);
        player.setPosition(500,500);
        players = new Array<Player>();
        players.add(player);
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

        //player.draw(spriteBatch);

        for (int i=0; i < players.size; i++) {
            players.get(i).texture = playerTexture;
            players.get(i).draw(spriteBatch);
        }

        spriteBatch.end();
    }

    private void update() {
        handleInput();
        Array<Player> p = this.connection.receiver.getPlayers();
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
                this.connection.sender.sendMessage("A");
            } catch (Exception e) {
                e.printStackTrace();
            }
            screenShiftX -= shiftX;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            try {
                this.connection.sender.sendMessage("D");
            } catch (Exception e) {
                e.printStackTrace();
            }
            screenShiftX += shiftX;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            try {
                this.connection.sender.sendMessage("W");
            } catch (Exception e) {
                e.printStackTrace();
            }
            screenShiftY += shiftY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            try {
                this.connection.sender.sendMessage("S");
            } catch (Exception e) {
                e.printStackTrace();
            }
            screenShiftY -= shiftY;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            //TODO send message about player has left the game
            Gdx.app.exit();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            //TODO Toggle fullscreen
        }
        if(screenShiftX > 1300 && mapPositionX < mapWidth - WBGame.WIDTH - 5){
            camera.translate(shiftX,0);
            screenShiftX -= shiftX;
            mapPositionX += shiftX;
        }
        if(screenShiftX < 300 && mapPositionX > 5){
            camera.translate(-shiftX,0);
            screenShiftX += shiftX;
            mapPositionX -= shiftX;
        }
        if(screenShiftY < 200 && mapPositionY > 5){
            camera.translate(0,-shiftY);
            screenShiftY += shiftY;
            mapPositionY -= shiftX;
        }
        if(screenShiftY > 700 && mapPositionY < mapHeight - WBGame.HEIGHT - 5){
            camera.translate(0,shiftY);
            screenShiftY -= shiftY;
            mapPositionY += shiftX;
        }
        camera.update();
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            MusicPlayer.initMusic("sounds/meow.mp3");
            MusicPlayer.playStaticMusic();
        }
    }
}
