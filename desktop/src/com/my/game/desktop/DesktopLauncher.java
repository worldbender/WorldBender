package com.my.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.my.game.Prosperites;
import com.my.game.WBGame;

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

		new LwjglApplication(new WBGame(), config);
	}
//	public static void toggleFullscreen(){
//		config.fullscreen = !config.fullscreen;
//	}
}
