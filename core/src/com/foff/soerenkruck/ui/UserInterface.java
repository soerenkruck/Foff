/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class UserInterface implements Screen {

    protected SpriteBatch defaultBatch;
    protected Stage defaultStage;

    protected UITexture uiTextures;
    protected String defaultName;

    protected TextButton.TextButtonStyle defaultButtonStyle;

    private BitmapFont largeFontRegular;
    private BitmapFont largeFontBold;
    private BitmapFont mediumFontRegular;
    private BitmapFont mediumFontBold;
    private BitmapFont smallFontRegular;
    private BitmapFont smallFontBold;

    protected Label.LabelStyle largeLabelStyle;
    protected Label.LabelStyle mediumLabelStyle;
    protected Label.LabelStyle smallLabelStyle;

    private Label title;

    public UserInterface(String name) {
        this.uiTextures = new UITexture();

        if (name == null) name = "null";
        this.defaultName = name;

        initActingElements();
    }

    public BitmapFont getLargeFontRegular() {
        return largeFontRegular;
    }
    public BitmapFont getLargeFontBold() {
        return largeFontBold;
    }
    public BitmapFont getMediumFontRegular() {
        return mediumFontRegular;
    }
    public BitmapFont getMediumFontBold() {
        return mediumFontBold;
    }
    public BitmapFont getSmallFontRegular() {
        return smallFontRegular;
    }
    public BitmapFont getSmallFontBold() {
        return smallFontBold;
    }

    @Override
    public void show() {

        /* TODO: Implement Background
        defaultBackground = new Sprite(new Texture(Gdx.files.internal("textures/backgrounds/menu_bg_2.png")));
        defaultBackground.setSize(1920, 1080);
        defaultBackground.setOrigin(defaultBackground.getWidth()/2, defaultBackground.getHeight()/2);
        defaultBackground.setPosition(0, 0);
         */

    }

    private void initActingElements() {
        defaultBatch = new SpriteBatch();
        defaultStage = new Stage();

        FreeTypeFontGenerator.FreeTypeFontParameter large = new FreeTypeFontGenerator.FreeTypeFontParameter();
        large.size = 48;
        FreeTypeFontGenerator.FreeTypeFontParameter medium = new FreeTypeFontGenerator.FreeTypeFontParameter();
        medium.size = 32;
        FreeTypeFontGenerator.FreeTypeFontParameter small = new FreeTypeFontGenerator.FreeTypeFontParameter();
        small.size = 24;

        FreeTypeFontGenerator regularGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LoRes12OT-Regular.ttf"));
        largeFontRegular = regularGenerator.generateFont(large);
        mediumFontRegular = regularGenerator.generateFont(medium);
        smallFontRegular = regularGenerator.generateFont(small);

        FreeTypeFontGenerator boldGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LoRes12OT-Bold.ttf"));
        largeFontBold = boldGenerator.generateFont(large);
        mediumFontBold = boldGenerator.generateFont(medium);
        smallFontBold = boldGenerator.generateFont(small);

        defaultStage = new Stage();
        Gdx.input.setInputProcessor(defaultStage);

        largeLabelStyle = new Label.LabelStyle();
        largeLabelStyle.font = largeFontRegular;
        largeLabelStyle.fontColor = Color.BLACK;

        mediumLabelStyle = new Label.LabelStyle();
        mediumLabelStyle.font = mediumFontRegular;
        mediumLabelStyle.fontColor = Color.BLACK;

        smallLabelStyle = new Label.LabelStyle();
        smallLabelStyle.font = smallFontRegular;
        smallLabelStyle.fontColor = Color.BLACK;

        title = new Label(defaultName, largeLabelStyle);
        title.scaleBy(2);
        title.setPosition(16, Gdx.graphics.getHeight() - 64);
        title.getStyle().fontColor = Color.WHITE;
        defaultStage.addActor(title);

        defaultButtonStyle = new TextButton.TextButtonStyle();
        mediumFontBold.setColor(Color.BLACK);
        defaultButtonStyle.font = mediumFontBold;
        defaultButtonStyle.up = uiTextures.getButtonIdle();
        defaultButtonStyle.down = uiTextures.getButtonDown();
        defaultButtonStyle.over = uiTextures.getButtonHover();
    }

    public void setTitle(String name) {
        title.setText(name);
    }

    public void setFontColor(Color color) {
        largeFontRegular.setColor(color);
        mediumFontRegular.setColor(color);
        smallFontRegular.setColor(color);
        largeFontBold.setColor(color);
        mediumFontBold.setColor(color);
        smallFontBold.setColor(color);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.08f, .15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        defaultBatch.begin();
        defaultBatch.end();

        defaultStage.act();
        defaultStage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        defaultStage.dispose();
        defaultBatch.dispose();
    }

}
