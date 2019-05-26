package com.my.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.my.game.Properties;
import com.my.game.bullets.ABullet;
import com.my.game.bullets.BulletList;
import com.my.game.maprenderer.GraphicMapHandler;
import com.my.game.opponents.*;
import com.my.game.pickups.*;
import com.my.game.player.Player;
import com.my.game.player.PlayerList;
import com.my.game.WBGame;
import com.my.game.music.MusicManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.Map;

public class GameplayScreen extends AbstractScreen{

    private static Logger logger = LogManager.getLogger(GameplayScreen.class.getName());
    private static Map<String, Player> players;
    private GraphicMapHandler graphicMapHandler;
    public static Player currentPlayer;
    private int numberOfXTiles;
    private int numberOfYTiles;
    private int tileWidth;
    private int tileHeight;
    private int mapWidth;
    private int mapHeight;
    public static final int PLAYER_TEXTURE_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_WIDTH"));
    public static final int PLAYER_TEXTURE_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_HEIGHT"));
    private float stateTime = 0f;
    private Hud hud;
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";
    private static final String UP = "UP";
    private static final String DOWN = "DOWN";

    public GameplayScreen(WBGame game) {
        super(game);
        this.create();
        hud = new Hud(spriteBatch, players);
        String startMap = Properties.loadConfigFile("START_MAP");
        changeLevel(startMap);
    }

    public void changeLevel(String map){
        Gdx.app.postRunnable(() -> {
            this.graphicMapHandler.LoadMap(map);
            this.numberOfXTiles = this.graphicMapHandler.getMap().getProperties().get("width", Integer.class);
            this.numberOfYTiles = this.graphicMapHandler.getMap().getProperties().get("height", Integer.class);
            this.tileWidth = this.graphicMapHandler.getMap().getProperties().get("tilewidth", Integer.class);
            this.tileHeight = this.graphicMapHandler.getMap().getProperties().get("tileheight", Integer.class);
            this.mapWidth = numberOfXTiles * tileWidth;
            this.mapHeight = numberOfYTiles * tileHeight;
            camera.position.x = (float)currentPlayer.getX() < (this.mapWidth - WBGame.WIDTH/2f) ? WBGame.WIDTH/2f : (this.mapWidth - WBGame.WIDTH/2f);
            camera.position.y = (float)currentPlayer.getY() < (this.mapHeight - WBGame.HEIGHT/2f) ?  WBGame.HEIGHT/2f: (this.mapHeight - WBGame.HEIGHT/2f);
            this.graphicMapHandler.getRender().setView(camera);
        });
    }

    @Override
    public void show(){

    }

    public void create() {
        this.init();
        if(!Boolean.parseBoolean(Properties.loadConfigFile("DEBUG_MODE"))){
            MusicManager.playBackgroundMusic();
        }
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
            hud.setHealthBarValue(player.getId(), player.getHp());
        }
        for(ABullet bullet : BulletList.getBullets()){
            bullet.draw(spriteBatch, stateTime);
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
            WBGame.getConnection().getSender().sendMessage(message);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    private void handleInput() {
        currentPlayer.resetKeys();
        this.handleMovementKeys();
        this.handleArrowKeys();
        this.handleSpecialKeys();
    }

    private void handleMovementKeys(){
        currentPlayer.setMoving(false);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey(LEFT);
            currentPlayer.setHeadDirection(LEFT);
            currentPlayer.KEY_A = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey(RIGHT);
            currentPlayer.setHeadDirection(RIGHT);
            currentPlayer.KEY_D = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey(UP);
            currentPlayer.setHeadDirection(UP);
            currentPlayer.KEY_W = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            currentPlayer.setMoving(true);
            currentPlayer.setActiveMovementKey(DOWN);
            currentPlayer.setHeadDirection(DOWN);
            currentPlayer.KEY_S = true;
        }
    }

    private void handleArrowKeys(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            currentPlayer.setHeadDirection(LEFT);
            currentPlayer.LEFT_ARROW = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            currentPlayer.setHeadDirection(RIGHT);
            currentPlayer.RIGHT_ARROW = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            currentPlayer.setHeadDirection(UP);
            currentPlayer.UP_ARROW = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            currentPlayer.setHeadDirection(DOWN);
            currentPlayer.DOWN_ARROW = true;
        }
    }

    private void handleSpecialKeys(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.changeScreen(WBGame.MENU_IN_GAME);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            game.switchScreenMode();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            MusicManager.playOpponentDieSound();
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
