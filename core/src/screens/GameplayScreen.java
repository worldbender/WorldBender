package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.my.game.Properties;
import com.my.game.bullets.ABullet;
import com.my.game.bullets.BulletList;
import com.my.game.opponents.*;
import com.my.game.connection.Connection;
import com.my.game.player.Player;
import com.my.game.player.PlayerList;
import com.my.game.WBGame;
import com.my.game.music.MusicPlayer;
import java.util.Map;

public class GameplayScreen extends AbstractScreen{

    private Texture playerTexture;
    private Texture bulletTexture;
    private static Map<String, Player> players;
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;
    public static Player currentPlayer;
    private int numerOfXTiles;
    private int numerOfYTiles;
    private int tileWidth;
    private int tileHeight;
    private int mapWidth;
    private int mapHeight;
    public static final int PLAYER_TEXTURE_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_WIDTH"));
    public static final int PLAYER_TEXTURE_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_HEIGHT"));
    public static final int NUMBER_OF_PLAYER_ANIMATION_FRAMES = 5;
    private Animation<TextureRegion> downWalkAnimation;
    private Animation<TextureRegion> upWalkAnimation;
    private Animation<TextureRegion> rightAnimation;
    private Animation<TextureRegion> leftAnimation;
    private float stateTime;
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
        this.create();
    }

    public void create() {
        this.loadData();
        this.init();
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.playMusic();
    }

    private void loadData() {
        playerTexture = new Texture("cat.png");
        bulletTexture = new Texture("granat.png");
        Schopenheuer.texture = new Texture("schopen.png");
        Nietzsche.texture = new Texture("nietzsche.png");
        Poe.texture = new Texture("poe.png");
        Texture walkSheet = new Texture("isaac/downIsaac.png");
        Texture upWalkSheet = new Texture("isaac/upIsaac.png");
        Texture leftSheet = new Texture("isaac/leftWalkIsaac.png");
        Texture rightSheet = new Texture("isaac/rightWalkIsaac.png");
        Player.downWalkAnimation = getAnimationFrom1DPicture(walkSheet, PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, NUMBER_OF_PLAYER_ANIMATION_FRAMES);
        Player.upWalkAnimation = getAnimationFrom1DPicture(upWalkSheet, PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, NUMBER_OF_PLAYER_ANIMATION_FRAMES);
        Player.rightWalkAnimation = getAnimationFrom1DPicture(rightSheet, PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 10);
        Player.leftWalkAnimation = getAnimationFrom1DPicture(leftSheet, PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 10);
        stateTime = 0f;
    }

    //TODO This method should be in asset manager
    private Animation<TextureRegion> getAnimationFrom1DPicture(Texture texture, int textureWidth, int textureHeight, int numberOfAnimationFrames){
        TextureRegion[][] arrayOfWalks = TextureRegion.split(texture, textureWidth, textureHeight);
        TextureRegion[] walkFrames = new TextureRegion[numberOfAnimationFrames];
        for (int i = 0; i < numberOfAnimationFrames; i++) {
            walkFrames[i] = arrayOfWalks[0][i];
        }
        return new Animation<TextureRegion>(0.1f, walkFrames);
    }

    private void init() {
        camera = new OrthographicCamera(WBGame.WIDTH, WBGame.HEIGHT);
        camera.translate(WBGame.WIDTH/2,WBGame.HEIGHT/2);
        players = PlayerList.getInstance();
        MusicPlayer.initMusic();
        MusicPlayer.playStaticMusic();
    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        super.render(delta);
        this.update();
        this.handleMapShift();
        render.setView(camera);
        render.render();

        spriteBatch.begin();
        this.drawAllMovableObjects(spriteBatch, stateTime);
        spriteBatch.end();

        this.sendMessageToServer("playerState:" + currentPlayer.getPlayerState());
    }

    private void drawAllMovableObjects(SpriteBatch spriteBatch, float stateTime){
        for(Player player : players.values()){
            player.draw(spriteBatch, stateTime);
        }
        for(ABullet bullet : BulletList.getBullets()){
            bullet.setTexture(bulletTexture);
            bullet.draw(spriteBatch);
        }
        for(AOpponent opponent : OpponentList.getOpponents()){
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
            WBGame.connection.sender.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInput() {
        this.handleMovementKeys();
        this.handleArrowKeys();
        this.handleSpecialKeys();
    }

    private void handleMovementKeys(){
        currentPlayer.setMoving(false);
        currentPlayer.resetWSAD();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey("LEFT");
            currentPlayer.KEY_A = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey("RIGHT");
            currentPlayer.KEY_D = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey("UP");
            currentPlayer.KEY_W = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey("DOWN");
            currentPlayer.KEY_S = true;
        }
    }

    private void handleArrowKeys(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.sendMessageToServer("createBullet:Tear:"+(float)Math.PI + ":");
            currentPlayer.setActiveMovementKey("LEFT");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.sendMessageToServer("createBullet:Tear:"+(float)0 + ":");
            currentPlayer.setActiveMovementKey("RIGHT");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.sendMessageToServer("createBullet:Tear:"+(float)Math.PI/2 + ":");
            currentPlayer.setActiveMovementKey("UP");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.sendMessageToServer("createBullet:Tear:"+(float)3 * Math.PI/2 + ":");
            currentPlayer.setActiveMovementKey("DOWN");
        }
    }

    private void handleSpecialKeys(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.changeScreen(WBGame.MENU);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            game.switchScreenMode();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            MusicPlayer.initMusic("sounds/meow.mp3");
            MusicPlayer.playStaticMusic();
        }
    }

}
