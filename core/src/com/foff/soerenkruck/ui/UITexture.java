/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class UITexture {

    private Skin defaultButtonSkin;

    public UITexture() {
        defaultButtonSkin = new Skin();
        defaultButtonSkin.addRegions(new TextureAtlas("ui/button/button.atlas"));
    }

    public Drawable getButtonDown() {
        return defaultButtonSkin.getDrawable("button_pressed");
    }
    public Drawable getButtonHover() {
        return defaultButtonSkin.getDrawable("button_hover");
    }
    public Drawable getButtonIdle() {
        return defaultButtonSkin.getDrawable("button_idle");
    }


}

