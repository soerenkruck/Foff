/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.foff.soerenkruck.ui.UserInterface;

public class MainMenu extends UserInterface implements Screen {

    Graphics.DisplayMode currentWindowMode;

    public MainMenu(String name) {
        super(name);
        setTitle("Fast one - First one");
    }

    @Override
    public void show() {
        super.show();

        setFontColor(Color.WHITE);

        int maxWidth = 0;

        currentWindowMode = Gdx.graphics.getDisplayMode();

        TextButton startSplitscreenGameButton = new TextButton("Neues Spiel", defaultButtonStyle);
        startSplitscreenGameButton.setPosition(32, Gdx.graphics.getHeight() - startSplitscreenGameButton.getHeight() - 98);
        startSplitscreenGameButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SplitscreenGameMenu("Neues Spiel"));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        defaultStage.addActor(startSplitscreenGameButton);

        TextButton startTrainGame = new TextButton("Trainieren", defaultButtonStyle);
        startTrainGame.setPosition(32, Gdx.graphics.getHeight() - startTrainGame.getHeight() * 2 - 98 - 16);
        startTrainGame.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new com.foff.soerenkruck.Game(1, false));

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        TextButton exitButton = new TextButton("Beenden", defaultButtonStyle);
        exitButton.setPosition(32, Gdx.graphics.getHeight() - exitButton.getHeight() * 3 - 98 - 32 );
        exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        if (startSplitscreenGameButton.getWidth() > maxWidth) maxWidth = (int) startSplitscreenGameButton.getWidth();
        if (startTrainGame.getWidth() > maxWidth) maxWidth = (int) startTrainGame.getWidth();
        if (exitButton.getWidth() > maxWidth) maxWidth = (int) exitButton.getWidth();

        maxWidth += 64;

        startSplitscreenGameButton.setWidth(maxWidth);
        startTrainGame.setWidth(maxWidth);
        exitButton.setWidth(maxWidth);

        defaultStage.addActor(startTrainGame);
        defaultStage.addActor(exitButton);

        Label versionLabel = new Label("0.1", smallLabelStyle);
        versionLabel.setPosition(Gdx.graphics.getWidth() - versionLabel.getWidth() - 16, 8);
        versionLabel.getStyle().fontColor = Color.WHITE;
        defaultStage.addActor(versionLabel);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        defaultBatch.begin();
        getMediumFontRegular().draw(defaultBatch, "Sich in Entwicklung befindend.", Gdx.graphics.getWidth()/2-150, Gdx.graphics.getHeight()/9*5);
        defaultBatch.end();
    }
}
