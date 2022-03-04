/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.foff.soerenkruck.ui.UserInterface;

import java.util.Random;

public class SplitscreenGameMenu extends UserInterface {

    public SplitscreenGameMenu(String name) {
        super(name);
    }

    @Override
    public void show() {
        super.show();

        TextButton threePlayerStartButton = new TextButton("3 Spieler", defaultButtonStyle);
        threePlayerStartButton.setWidth(threePlayerStartButton.getWidth() + 32);
        threePlayerStartButton.setPosition(Gdx.graphics.getWidth() / 2 - threePlayerStartButton.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        threePlayerStartButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new com.foff.soerenkruck.Game(3, new Random().nextBoolean()));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        defaultStage.addActor(threePlayerStartButton);

        TextButton twoPlayerStartButton = new TextButton("2 Spieler", defaultButtonStyle);
        twoPlayerStartButton.setWidth(twoPlayerStartButton.getWidth() + 32);
        twoPlayerStartButton.setPosition(threePlayerStartButton.getX() - twoPlayerStartButton.getWidth() - 32, Gdx.graphics.getHeight() / 2);
        twoPlayerStartButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new com.foff.soerenkruck.Game(2, new Random().nextBoolean()));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        defaultStage.addActor(twoPlayerStartButton);

        TextButton fourPlayerStartButton = new TextButton("4 Spieler", defaultButtonStyle);
        fourPlayerStartButton.setWidth(fourPlayerStartButton.getWidth() + 32);
        fourPlayerStartButton.setPosition(threePlayerStartButton.getX() + threePlayerStartButton.getWidth() + 32, Gdx.graphics.getHeight() / 2);
        fourPlayerStartButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new com.foff.soerenkruck.Game(4, new Random().nextBoolean()));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        defaultStage.addActor(fourPlayerStartButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(null));

        defaultStage.act();
        defaultStage.draw();
    }
}
