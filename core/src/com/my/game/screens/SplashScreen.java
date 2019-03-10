package com.my.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import com.my.game.WBGame;

public class SplashScreen extends AbstractScreen{
    private Texture splashImg;

    public SplashScreen(final WBGame game) {
        super(game);
        Timer.schedule(new Task() {
            @Override
            public void run() {
                game.setScreen(new MenuScreen(game));
            }
        }, 2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        drawBackground();
        showScreenMessage("Connecting ...");
    }
}
