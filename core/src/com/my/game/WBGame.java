package com.my.game;

import com.badlogic.gdx.Game;
import screens.SplashScreen;
import screens.MenuScreen;
import screens.GameplayScreen;
import java.awt.*;

public class WBGame extends Game {

    private GameplayScreen gameplayScreen;
    private MenuScreen menuScreen;

    public final static int MENU = 0;
    public final static int PLAY = 1;
//    public final static int APPLICATION = 2;

    public final static String GAME_NAME = "World Bender";
    public static int WIDTH;
    public static int HEIGHT;

    private boolean paused;

    public WBGame(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = screenSize.width;
        HEIGHT = screenSize.height;
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
                if(menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case PLAY:
                if(gameplayScreen == null) gameplayScreen = new GameplayScreen(this);
                this.setScreen(gameplayScreen);
                break;
        }
    }
}