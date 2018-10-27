package com.my.game;

import com.badlogic.gdx.Game;
import screens.SplashScreen;

public class WBGame extends Game {

    public final static String GAME_NAME = "World Bender";
    public final static int WIDTH = 480;
    public final static int HEIGHT = 600;

    private boolean paused;

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