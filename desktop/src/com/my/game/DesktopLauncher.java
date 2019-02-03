package com.my.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	static LwjglApplicationConfiguration config;
	public static void main (String[] arg) {

		config = new LwjglApplicationConfiguration();

		config.title = WBGame.GAME_NAME;
		config.width = WBGame.WIDTH;
		config.height = WBGame.HEIGHT;

		config.resizable = true;
		config.fullscreen = false;
		config.forceExit = true;
		config.foregroundFPS = 0;

		new LwjglApplication(new WBGame(), config);
	}
}
