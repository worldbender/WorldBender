package com.my.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import screens.RoomScreen;
import screens.SplashScreen;
import screens.MenuScreen;
import screens.GameplayScreen;
import java.awt.*;

public class WBGame extends Game {

    private GameplayScreen gameplayScreen;
    private MenuScreen menuScreen;
    private RoomScreen roomScreen;

    public final static int MENU = 0;
    public final static int PLAY = 1;
    public final static int ROOM = 2;

    private int currentRoom = 0;

    public final static String SERVER_ADRESS = Prosperites.loadConfigFile("ip");
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