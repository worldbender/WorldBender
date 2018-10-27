package com.my.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
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