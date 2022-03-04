package com.foff.soerenkruck.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.foff.soerenkruck.Start;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		if (arg.length > 0) {
			config.fullscreen = Boolean.getBoolean(arg[0]);
			config.width = 1280;
			config.height = 720;
		} else {
			config.fullscreen = true;
			config.width = 1920;
			config.height = 1080;
		}

		config.title = "Fast one - First one";

		new LwjglApplication(new Start(), config);
	}
}
