package com.my.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.my.game.Properties;
import com.my.game.bullets.ABullet;
import com.my.game.bullets.BulletList;
import com.my.game.bullets.SpectralTear;
import com.my.game.bullets.Tear;
import com.my.game.mapRenderer.GraphicMapHandler;
import com.my.game.opponents.*;
import com.my.game.pickups.*;
import com.my.game.player.Player;
import com.my.game.player.PlayerList;
import com.my.game.WBGame;
import com.my.game.music.MusicPlayer;
import org.json.JSONObject;

import java.util.Map;

public class GameplayScreen extends AbstractScreen{
    private static Map<String, Player> players;
    private GraphicMapHandler graphicMapHandler;
    public static Player currentPlayer;
    private int numerOfXTiles;
    private int numerOfYTiles;
    private int tileWidth;
    private int tileHeight;
    private int mapWidth;
    private int mapHeight;
    public static final int PLAYER_TEXTURE_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_WIDTH"));
    public static final int PLAYER_TEXTURE_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_HEIGHT"));
    public static final int PLAYER_HEAD_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_HEAD_WIDTH"));
    public static final int PLAYER_HEAD_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_HEAD_HEIGHT"));
    public static final int NUMBER_OF_PLAYER_ANIMATION_FRAMES = 5;
    private float stateTime;
    private Hud hud;

    public GameplayScreen(WBGame game) {
        super(game);
        this.create();
        hud = new Hud(spriteBatch, players);
    }

    public void changeLevel(String map){
        Gdx.app.postRunnable(() -> this.graphicMapHandler.LoadMap(map));
        this.numerOfXTiles = this.graphicMapHandler.getMap().getProperties().get("width", Integer.class);
        this.numerOfYTiles = this.graphicMapHandler.getMap().getProperties().get("height", Integer.class);
        this.tileWidth = this.graphicMapHandler.getMap().getProperties().get("tilewidth", Integer.class);
        this.tileHeight = this.graphicMapHandler.getMap().getProperties().get("tileheight", Integer.class);
        this.mapWidth = numerOfXTiles * tileWidth;
        this.mapHeight = numerOfYTiles * tileHeight;
        camera.position.x = (float)currentPlayer.getX() < (this.mapWidth - WBGame.WIDTH/2f) ? WBGame.WIDTH/2f : (this.mapWidth - WBGame.WIDTH/2f);
        camera.position.y = (float)currentPlayer.getY() < (this.mapHeight - WBGame.HEIGHT/2f) ?  WBGame.HEIGHT/2f: (this.mapHeight - WBGame.HEIGHT/2f);
        Gdx.app.postRunnable(() -> this.graphicMapHandler.getRender().setView(camera));
    }

    @Override
    public void show(){
        String startMap = Properties.loadConfigFile("START_MAP");
        changeLevel(startMap);
    }

    public void create() {
        this.loadData();
        this.init();
        MusicPlayer.initSounds();
        if(!Boolean.parseBoolean(Properties.loadConfigFile("DEBUG_MODE"))){
            MusicPlayer.playBackgroundMusic();
        }
    }

    private void loadData() {
        Tear.tearTexture = new Texture("bullets/granat.png");
        SpectralTear.spectralTearTexture = new Texture("bullets/spectralTear.png");
        Schopenheuer.texture = new Texture("opponents/schopen.png");
        Nietzsche.texture = new Texture("opponents/nietzsche.png");
        Poe.texture = new Texture("opponents/poe.png");
        Texture walkSheet = new Texture("isaac/downIsaacHeadless2.png");
        Texture upWalkSheet = new Texture("isaac/upIsaacHeadless2.png");
        Texture leftSheet = new Texture("isaac/leftWalkIsaacHeadless.png");
        Texture rightSheet = new Texture("isaac/rightWalkIsaacHeadless.png");
        Texture heads = new Texture("isaac/prof.png");
        Player.downWalkAnimation = getAnimationFrom1DPicture(walkSheet, PLAYER_TEXTURE_WIDTH*2, PLAYER_TEXTURE_HEIGHT*2, NUMBER_OF_PLAYER_ANIMATION_FRAMES);
        Player.upWalkAnimation = getAnimationFrom1DPicture(upWalkSheet, PLAYER_TEXTURE_WIDTH*2, PLAYER_TEXTURE_HEIGHT*2, NUMBER_OF_PLAYER_ANIMATION_FRAMES);
        Player.rightWalkAnimation = getAnimationFrom1DPicture(rightSheet, PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 10);
        Player.leftWalkAnimation = getAnimationFrom1DPicture(leftSheet, PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 10);
        Player.heads = getAnimationFrom1DPicture(heads, PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 4);
        HpPickup.texture = new Texture("pickups/hp.png");
        InnerEye.texture = new Texture("pickups/InnerEye.png");
        SadOnion.texture = new Texture("pickups/SadOnion.png");
        Warp.texture = new Texture("pickups/warp.png");
        Texture warpAnimation = new Texture("pickups/warpAnimated.png");
        Warp.warpAnimation = getAnimationFrom1DPicture(warpAnimation, 64, 64, 9);
        stateTime = 0f;
        Player.headRegion = new TextureRegion(heads, 0, 0, PLAYER_HEAD_WIDTH, PLAYER_HEAD_HEIGHT);
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
        this.graphicMapHandler = new GraphicMapHandler();
        players = PlayerList.getInstance();
    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        super.render(delta);
        this.update();
        this.handleMapShift();
        camera.update();
        this.graphicMapHandler.getRender().setView(camera);
        this.graphicMapHandler.getRender().render();

        spriteBatch.begin();
        this.drawAllMovableObjects(spriteBatch, stateTime);
        spriteBatch.end();

        spriteBatch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().act(delta);
        hud.getStage().draw();

        this.sendMessageToServer(new JSONObject()
                .put("msg", "playerState")
                .put("content", currentPlayer.getPlayerState()));
    }

    private void drawAllMovableObjects(SpriteBatch spriteBatch, float stateTime){
        for(Player player : players.values()){
            player.draw(spriteBatch, stateTime);
            hud.setHealthBarValue(player.getName(), player.getHp());
        }
        for(ABullet bullet : BulletList.getBullets()){
            bullet.draw(spriteBatch);
        }
        for(AOpponent opponent : OpponentList.getOpponents()){
            opponent.draw(spriteBatch);
        }
        for(APickup pickup : PickupList.getPickups()){
            pickup.draw(spriteBatch, stateTime);
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

    private void sendMessageToServer(JSONObject message){
        try {
            WBGame.connection.sender.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInput() {
        currentPlayer.resetkeys();
        this.handleMovementKeys();
        this.handleArrowKeys();
        this.handleSpecialKeys();
    }

    private void handleMovementKeys(){
        currentPlayer.setMoving(false);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey("LEFT");
            currentPlayer.setHeadDirection("LEFT");
            currentPlayer.KEY_A = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey("RIGHT");
            currentPlayer.setHeadDirection("RIGHT");
            currentPlayer.KEY_D = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey("UP");
            currentPlayer.setHeadDirection("UP");
            currentPlayer.KEY_W = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey("DOWN");
            currentPlayer.setHeadDirection("DOWN");
            currentPlayer.KEY_S = true;
        }
    }

    private void handleArrowKeys(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            currentPlayer.setHeadDirection("LEFT");
            currentPlayer.LEFT_ARROW = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            currentPlayer.setHeadDirection("RIGHT");
            currentPlayer.RIGHT_ARROW = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            currentPlayer.setHeadDirection("UP");
            currentPlayer.UP_ARROW = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            currentPlayer.setHeadDirection("DOWN");
            currentPlayer.DOWN_ARROW = true;
        }
    }

    private void handleSpecialKeys(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.changeScreen(WBGame.MENU_IN_GAME);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            game.switchScreenMode();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            MusicPlayer.playOpponentDieSound();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            currentPlayer.KEY_SPACE = true;
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        hud.getStage().getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        hud.dispose();
        spriteBatch.dispose();
    }

}
