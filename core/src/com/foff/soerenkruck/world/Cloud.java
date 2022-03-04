/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Cloud {

    private Rectangle rectangle;
    private Sprite sprite;

    private int speed;

    public static final int UNSPECIFIC = -1;

    public Cloud(int x) {

        int w = new Random().nextInt(80) + 32;
        speed = w / 18;

        int height = new Random().nextInt(256) + Cell.SIZE * 16;

        if (x != UNSPECIFIC)
            this.rectangle = new Rectangle(x, height, w * 2, w);
        else
            this.rectangle = new Rectangle(World.RANGE * Cell.SIZE, height, w * 2, w);

        sprite = new Sprite(new Texture("cloud.png"));
        update();
    }

    private void update() {
        sprite.setBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public float getX() {
        return rectangle.getX();
    }

    public void draw(SpriteBatch batch, float delta) {
        rectangle.x -= speed * delta;
        update();
        sprite.draw(batch);
    }
}
