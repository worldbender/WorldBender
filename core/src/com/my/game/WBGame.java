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
    private MenuScreen menuScreen;
    private RoomScreen roomScreen;

    public final static int SPLASH = 0;
    public final static int MENU = 1;
    public final static int MENU_FULL_ROOM = 2;
    public final static int MENU_NO_ROOM = 3;
    public final static int MENU_IN_GAME = 4;
    public final static int PLAY = 5;
    public final static int ROOM = 6;
    public final static int ROOM_OWNER = 7;
    private int currentRoom = 0;
    public final static String SERVER_ADDRESS = Properties.loadConfigFile("IP");
    public final static boolean IS_DEBUG_VERSION = true;
//    public final static int APPLICATION = 2;

    public final static String GAME_NAME = "World Bender";
    public static int WIDTH;
    public static int HEIGHT;
    public static int RES_WIDTH;
    public static int RES_HEIGHT;

    private boolean paused;

    public WBGame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        RES_WIDTH = screenSize.width;
        RES_HEIGHT = screenSize.height;
        WIDTH = Integer.parseInt(Properties.loadConfigFile("SEE_RANGE_WIDTH"));
        HEIGHT = Integer.parseInt(Properties.loadConfigFile("SEE_RANGE_HEIGHT"));
    }

    @Override
    public void create () {
        MyAssetManager.loadAllAssets();
        MyAssetManager.manager.finishLoading();
//        while(!MyAssetManager.manager.update())
//            System.out.println(MyAssetManager.manager.getProgress() * 100 + " %");
        this.connection = new Connection(this);
        this.setScreen(new SplashScreen(this));
    }

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