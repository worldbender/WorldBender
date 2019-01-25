package com.my.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.my.game.connection.Connection;
import screens.*;
import screens.SplashScreen;

import java.awt.*;

public class WBGame extends Game {

    public static Connection connection;
    public static boolean connectionStatus = false;

    private GameplayScreen gameplayScreen;
    private MenuScreen menuScreen;
    private RoomScreen roomScreen;

    public final static int SPLASH = 0;
    public final static int MENU = 1;
    public final static int PLAY = 2;
    public final static int ROOM = 3;
    public final static int ROOM_OWNER = 4;
    private int currentRoom = 0;
    public final static String SERVER_ADDRESS = Properties.loadConfigFile("ip");
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
        WIDTH = 1600;
        HEIGHT = 900;
    }

    @Override
    public void create () {
        this.connection = new Connection(this);
        this.setScreen(new SplashScreen(this));
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
            case PLAY:
                if(gameplayScreen == null) gameplayScreen = new GameplayScreen(this);
                this.setScreen(gameplayScreen);
                break;
            case ROOM:
                this.setScreen(new RoomScreen(this));
                break;
            case ROOM_OWNER:
                this.setScreen(new RoomScreen(this, true));
                break;
        }
    }

    public void switchScreenMode(){
        Boolean fullScreen = Gdx.graphics.isFullscreen();
        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
        if (fullScreen)
            Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
        else
            Gdx.graphics.setFullscreenMode(currentMode);
    }
}