/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.controller;

import com.badlogic.gdx.graphics.Texture;

public class StatesIcons {

    private Texture freezeTexture, doubleSpeedTexture, nullTexture, halfSpeedTexture, highJumpTexture;

    public StatesIcons() {
        freezeTexture = new Texture("power_freeze.png");
        doubleSpeedTexture = new Texture("power_doublespeed.png");
        nullTexture = new Texture("null.png");
        halfSpeedTexture = new Texture("power_half.png");
        highJumpTexture = new Texture("power_jump.png");
    }

    public Texture getFreezeTexture() {
        return freezeTexture;
    }

    public Texture getDoubleSpeedTexture() {
        return doubleSpeedTexture;
    }

    public Texture getNullTexture() {
        return nullTexture;
    }

    public Texture getHalfSpeedTexture() {
        return halfSpeedTexture;
    }

    public Texture getHighJumpTexture() {
        return highJumpTexture;
    }
}
