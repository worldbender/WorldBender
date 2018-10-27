package com.my.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.my.game.WBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = WBGame.GAME_NAME;
		config.width = WBGame.WIDTH;
		config.height = WBGame.HEIGHT;
		config.resizable = false;

		new LwjglApplication(new WBGame(), config);
	}
}
