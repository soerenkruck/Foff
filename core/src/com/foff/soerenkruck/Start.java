/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.foff.soerenkruck.screen.MainMenu;

public class Start extends Game {

    public Start() {
    }

    @Override
    public void create() {
        ((Start) Gdx.app.getApplicationListener()).setScreen(new MainMenu("Foff"));
        this.dispose();
    }

}
