package com.my.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class ServerCreator {
    static LwjglApplicationConfiguration config;
    public static void main (String[] arg) {
        config = new LwjglApplicationConfiguration();
        LwjglApplication lwjglApplication = new LwjglApplication(new Server(), config);
    }
    public static void toggleFullscreen(){
        config.fullscreen = !config.fullscreen;
    }
}
