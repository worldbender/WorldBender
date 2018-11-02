package com.my.game;

import com.badlogic.gdx.Game;
import screens.SplashScreen;
import java.awt.*;

public class WBGame extends Game {

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
}