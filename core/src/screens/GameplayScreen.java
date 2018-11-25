package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.my.game.Connection.Connection;
import com.my.game.Player.Player;
import com.my.game.Player.PlayerList;
import com.my.game.WBGame;
import com.my.game.music.MusicPlayer;
import java.util.Map;

public class GameplayScreen extends AbstractScreen{

    private Texture playerTexture;
    private static Map<String, Player> players;
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;
    public Connection connection;
    boolean connectionStatus;

    @Override
    public void show(){
        map = new TmxMapLoader().load("maps/t9.tmx");
        render = new OrthogonalTiledMapRenderer(map);

    }

    public GameplayScreen(WBGame game) {
        super(game);
        connection = new Connection();
        create();
    }

    public void create() {
        try{
            connection.createConnection();
            connectionStatus = true;
        }catch(Exception e){
            System.out.println("Nie nawiązano połączenia");
            connectionStatus = false;
        }
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
        camera.translate(WBGame.WIDTH/2,WBGame.HEIGHT/2);
        Player player = new Player(playerTexture, true);
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
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            super.render(delta);
            update();

            Gdx.gl.glClearColor(1, 1, 1, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            render.setView(camera);
            render.render();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            if(WBGame.IS_DEBUG_VERSION){
                for(Player player : players.values()){
                    shapeRenderer.rect((float)player.getX(), (float)player.getY() , 56, 56);
                }
            }
            shapeRenderer.end();
            spriteBatch.begin();
            spriteBatch.setProjectionMatrix(camera.combined);

            //player.draw(spriteBatch);

            for(Player player : players.values()){
                player.texture = playerTexture;
                player.draw(spriteBatch);
            }

            spriteBatch.end();
        }
    }

    private void update() {
        handleInput();
        //camera.update();
        //camera.position.set(player.x + player.width / 2,
        //        player.y + 300, 0);

    }

    private void handleInput() {
        int numerOfXTiles = map.getProperties().get("width", Integer.class);
        int numerOfYTiles = map.getProperties().get("height", Integer.class);
        int mapWidth = numerOfXTiles * 32;
        int mapHeight = numerOfYTiles * 32;
        int shiftX = 5;
        int shiftY = 5;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            try {
                this.connection.sender.sendMessage("A");
            } catch (Exception e) {
                e.printStackTrace();
            }
           // screenShiftX -= shiftX;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            try {
                this.connection.sender.sendMessage("D");
            } catch (Exception e) {
                e.printStackTrace();
            }
           // screenShiftX += shiftX;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            try {
                this.connection.sender.sendMessage("W");
            } catch (Exception e) {
                e.printStackTrace();
            }
          //  screenShiftY += shiftY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            try {
                this.connection.sender.sendMessage("S");
            } catch (Exception e) {
                e.printStackTrace();
            }
          //  screenShiftY -= shiftY;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.changeScreen(WBGame.MENU);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F11)){
            game.switchScreenMode();
        }
       /* if(screenShiftX > 1300 && mapPositionX < mapWidth - WBGame.WIDTH - 5){
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
        }*/
        camera.update();
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            MusicPlayer.initMusic("sounds/meow.mp3");
            MusicPlayer.playStaticMusic();
        }
    }
}
