package com.my.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.my.game.connection.Connection;
import com.my.game.screens.*;
import com.my.game.screens.SplashScreen;

import java.awt.*;

public class WBGame extends Game {

    public static Connection connection;
    public static boolean connectionStatus = false;
    private GameplayScreen gameplayScreen;

    public static final int SPLASH = 0;
    public static final int MENU = 1;
    public static final int MENU_FULL_ROOM = 2;
    public static final int MENU_NO_ROOM = 3;
    public static final int MENU_IN_GAME = 4;
    public static final int PLAY = 5;
    public static final int ROOM = 6;
    public static final int ROOM_OWNER = 7;
    public static final String SERVER_ADDRESS = Properties.loadConfigFile("IP");

    public static final String GAME_NAME = "World Bender";
    public static int WIDTH = Integer.parseInt(Properties.loadConfigFile("SEE_RANGE_WIDTH"));
    public static int HEIGHT = Integer.parseInt(Properties.loadConfigFile("SEE_RANGE_HEIGHT"));
    public static int RES_WIDTH;
    public static int RES_HEIGHT;

    private boolean paused;

    public WBGame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        RES_WIDTH = screenSize.width;
        RES_HEIGHT = screenSize.height;
    }

    @Override
    public void create () {
        MyAssetManager.loadAllAssets();
        MyAssetManager.manager.finishLoading();
        this.connection = new Connection(this);
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
            case MENU_FULL_ROOM:
                this.setScreen(new MenuScreen(this, true, true));
                break;
            case MENU_NO_ROOM:
                this.setScreen(new MenuScreen(this, false, false));
                break;
            case MENU_IN_GAME:
                this.setScreen(new MenuScreen(this, true));
                break;
            case PLAY:
                if(gameplayScreen == null) gameplayScreen = new GameplayScreen(this);
                this.setScreen(gameplayScreen);
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