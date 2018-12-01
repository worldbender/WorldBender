package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.my.game.CBullets.ABullet;
import com.my.game.CBullets.BulletList;
import com.my.game.COpponents.AOpponent;
import com.my.game.COpponents.OpponentList;
import com.my.game.Connection.Connection;
import com.my.game.Player.Player;
import com.my.game.Player.PlayerList;
import com.my.game.WBGame;
import com.my.game.music.MusicPlayer;
import java.util.Map;

public class GameplayScreen extends AbstractScreen{

    private Texture playerTexture;
    private Texture bulletTexture;
    private Texture opponentTexture;
    private static Map<String, Player> players;
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;
    private Connection connection;
    boolean connectionStatus;
    public static Player currentPlayer;
    private int numerOfXTiles;
    private int numerOfYTiles;
    private int tileWidth;
    private int tileHeight;
    private int mapWidth;
    private int mapHeight;
    @Override
    public void show(){
        map = new TmxMapLoader().load("maps/t9.tmx");
        render = new OrthogonalTiledMapRenderer(map);
        this.numerOfXTiles = map.getProperties().get("width", Integer.class);
        this.numerOfYTiles = map.getProperties().get("height", Integer.class);
        this.tileWidth = map.getProperties().get("tilewidth", Integer.class);
        this.tileHeight = map.getProperties().get("tileheight", Integer.class);
        this.mapWidth = numerOfXTiles * tileWidth;
        this.mapHeight = numerOfYTiles * tileHeight;
    }

    public GameplayScreen(WBGame game) {
        super(game);
        connection = new Connection();
        this.create();
    }

    public void create() {
        try{
            connection.createConnection();
            connectionStatus = true;
        }catch(Exception e){
            System.out.println("Nie nawiązano połączenia");
            connectionStatus = false;
        }
        this.loadData();
        this.init();
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.playMusic();
    }

    private void loadData() {
        playerTexture = new Texture("cat.png");
        bulletTexture = new Texture("granat.png");
        opponentTexture = new Texture("schopen.png");
    }

    private void init() {
        camera = new OrthographicCamera(WBGame.WIDTH, WBGame.HEIGHT);
        camera.translate(WBGame.WIDTH/2,WBGame.HEIGHT/2);
        players = PlayerList.getInstance();
    }

    @Override
    public void render(float delta) {
        if(!connectionStatus){
            game.changeScreen(WBGame.MENU);
            try{
                connection.createConnection();
                connectionStatus = true;
                game.changeScreen(WBGame.PLAY);
            }catch(Exception e){
                System.out.println("Nie nawiązano połączenia");
            }
        }
        else{
            super.render(delta);
            this.update();

            Gdx.gl.glClearColor(1, 1, 1, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            render.setView(camera);
            render.render();
            this.handleMapShift();
            spriteBatch.begin();
            spriteBatch.setProjectionMatrix(camera.combined);
            this.drawAllMovableObjects(spriteBatch);

            spriteBatch.end();
        }
    }

    private void drawAllMovableObjects(SpriteBatch spriteBatch){
        for(Player player : players.values()){
            player.texture = playerTexture;
            player.draw(spriteBatch);
        }
        for(ABullet bullet : BulletList.getBullets()){
            bullet.setTexture(bulletTexture);
            bullet.draw(spriteBatch);
        }
        for(AOpponent opponent : OpponentList.getOpponents()){
            opponent.setTexture(opponentTexture);
            opponent.draw(spriteBatch);
        }
    }

    private void update() {
        handleInput();
    }

    private boolean shouldScreenShiftX(){
        return currentPlayer.getX() >= WBGame.WIDTH/2 && currentPlayer.getX() < (this.mapWidth - WBGame.WIDTH/2);
    }
    private boolean shouldScreenShiftY(){
        return currentPlayer.getY() >= WBGame.HEIGHT/2 && currentPlayer.getY() < (this.mapHeight - WBGame.HEIGHT/2);
    }

    private void handleMapShift(){

        if(this.shouldScreenShiftX()){
            camera.position.x = (float)currentPlayer.getX();
        }
        if(this.shouldScreenShiftY()){
            camera.position.y = (float)currentPlayer.getY();
        }
    }

    private void sendMessageToServer(String message){
        try {
            this.connection.sender.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.sendMessageToServer("A");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.sendMessageToServer("D");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.sendMessageToServer("W");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.sendMessageToServer("S");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.sendMessageToServer("createBullet:Tear:"+(float)Math.PI);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.sendMessageToServer("createBullet:Tear:"+(float)0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.sendMessageToServer("createBullet:Tear:"+(float)Math.PI/2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.sendMessageToServer("createBullet:Tear:"+(float)3 * Math.PI/2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.changeScreen(WBGame.MENU);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            game.switchScreenMode();
        }
        camera.update();
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            MusicPlayer.initMusic("sounds/meow.mp3");
            MusicPlayer.playStaticMusic();
        }
    }
}
