package com.my.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.my.game.connection.Connection;
import com.my.game.music.MusicManager;
import com.my.game.rooms.Room;
import com.my.game.screens.*;
import com.my.game.screens.SplashScreen;

import java.util.List;

public class WBGame extends Game {

    private static Connection connection;
    public static boolean connectionStatus = false;
    private GameplayScreen gameplayScreen;
    private PreferencesScreen preferencesScreen;

    public static final int SPLASH = 0;
    public static final int MENU = 1;
    public static final int MENU_IN_GAME = 2;
    public static final int PLAY = 3;
    public static final int ROOM = 4;
    public static final int ROOM_OWNER = 5;
    public static final int ROOM_LIST = 6;
    public static final int PREFERENCES = 7;


    public static final String SERVER_ADDRESS = Properties.loadConfigFile("IP");

    public static final String GAME_NAME = "World Bender";
    public static final int WIDTH = Integer.parseInt(Properties.loadConfigFile("SEE_RANGE_WIDTH"));
    public static final int HEIGHT = Integer.parseInt(Properties.loadConfigFile("SEE_RANGE_HEIGHT"));

    private boolean paused;

    public static Connection getConnection(){
        return connection;
    }

    @Override
    public void create () {
        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
        if (AppPreferences.isFullScreenEnabled())
            Gdx.graphics.setFullscreenMode(currentMode);
        else
            Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);

        MyAssetManager.loadAllAssets();
        MyAssetManager.manager.finishLoading();
        MusicManager.initSounds();
        MusicManager.setMusicVolume(AppPreferences.getMusicVolume());
        MusicManager.playMenuMusic();
        connection = new Connection(this);
        this.setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose () {
        super.dispose();
        MyAssetManager.dispose();
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void changeScreen(int screen){
        switch(screen){
            case SPLASH:
                this.setScreen(new SplashScreen(this));
                break;
            case MENU:
                this.setScreen(new MenuScreen(this));
                break;
            case MENU_IN_GAME:
                this.setScreen(new MenuScreen(this, true));
                break;
            case PLAY:
                if(gameplayScreen == null) gameplayScreen = new GameplayScreen(this);
                this.setScreen(gameplayScreen);
                break;
            case PREFERENCES:
                if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                break;
            default:
        }
    }

    public void changeScreen(int screen, int roomId){
        switch(screen){
            case ROOM_OWNER:
                this.setScreen(new RoomScreen(this, true, roomId));
                break;
            case ROOM:
                this.setScreen(new RoomScreen(this, roomId));
                break;
            default:
        }
    }

    public void changeScreen(int screen, String character, int option, List<Room> rooms){
        switch(screen){
            case ROOM_LIST:
                this.setScreen(new RoomListScreen(this, rooms, character, option));
                break;
            default:
        }
    }

    public void switchScreenMode(){
        boolean fullScreen = Gdx.graphics.isFullscreen();
        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
        if (fullScreen)
            Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
        else
            Gdx.graphics.setFullscreenMode(currentMode);
    }
    public GameplayScreen getGameplayScreen(){
        return this.gameplayScreen;
    }
}