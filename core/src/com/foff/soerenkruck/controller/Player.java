/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.foff.soerenkruck.Game;
import com.foff.soerenkruck.ui.UserInterface;
import com.foff.soerenkruck.world.World;
import com.foff.soerenkruck.world.object.Wall;

import java.util.Random;


public class Player {

    private Sprite playerSprite, playerStateSprite;
    public static final int SIZE = 32;

    Vector2 move;
    boolean jump = false;

    private boolean win = false;

    private PlayerState playerState = PlayerState.Normal;
    float stateDuration = 0;
    float stateTime = 0;

    private Rectangle playerRectangle;

    private final int NORMAL_SPEED = 150;

    private Texture rightTexture, leftTexture;

    private Game game;

    private float seconds = 0;
    private int minutes = 0;

    private int finishedPos;

    private StatesIcons statesIcons;

    int pos = 0;

    private BitmapFont timeFont, remainFont;

    public Player(int y, Game game) {
        // FIRST ONE FINISH FIRST

        this.game = game;
        move = new Vector2(NORMAL_SPEED, 0);

        rightTexture = new Texture("player.png");
        leftTexture = new Texture("player_inverse.png");
        playerSprite = new Sprite(rightTexture);

        playerRectangle = new Rectangle(0, y, SIZE, SIZE);
        playerSprite.setBounds(playerRectangle.x, playerRectangle.y, SIZE, SIZE);

        statesIcons = new StatesIcons();
        playerStateSprite = new Sprite(statesIcons.getNullTexture());
        playerStateSprite.setSize(64, 64);

        UserInterface userInterface = new UserInterface(null);
        timeFont = userInterface.getSmallFontRegular();
        remainFont = userInterface.getLargeFontBold();
    }

    public void increaseTime(float delta) {
        if (win) return;
        seconds += delta;

        if (seconds >= 60) {
            minutes++;
            seconds = 0;
        }
    }

    public void drawPlayerState(SpriteBatch batch, Camera camera) {
        playerStateSprite.setPosition(camera.position.x - camera.viewportWidth / 2 + 16, camera.position.y + camera.viewportHeight / 2 - 16 - 64);
        playerStateSprite.draw(batch);
    }

    public String getTimeString() {
        return minutes + ":" + String.format("%.1f", seconds);
    }

    public void move(float delta, World world, boolean fast) {

        if (delta > 1) return;

        // Calcs for Enchantment
        if (playerState != PlayerState.Normal) {
            stateTime += delta;
            if (stateTime > stateDuration)  {
                stateTime = 0;
                disenchantPlayer();
            }
        }

        if (!win && this.playerState != PlayerState.Freezed) {
            // Horizontale Bewegung
            playerRectangle.x += move.x * delta;
            if (world.hits(playerRectangle)) {
                playerRectangle.x -= move.x * delta;
                move.x = -move.x;
                changeSpriteTexture();
            }

            // Vertikale Bewegung
            if (fast) move.y -= 9.8f * delta * 120;
            else move.y -= 9.8f * delta * 100;
            if (move.y <= 0 && move.y > -50) move.y = -50;

            playerRectangle.y += move.y * delta;
            if (world.hits(playerRectangle)) {
                jump = false;
                playerRectangle.y -= move.y * delta;
                move.y = 0;
            }

            playerSprite.setPosition(playerRectangle.x, playerRectangle.y);

            if (world.hitsItem(playerRectangle)) {
                disenchantPlayer();
                enchantPlayer();
            }
        }
    }

    public void draw(SpriteBatch batch, int remain) {
        Vector2 center = playerRectangle.getCenter(new Vector2(playerRectangle.x, playerRectangle.y));
        playerSprite.draw(batch);

        this.pos = World.RANGE - remain + World.INIT_VAL;

        if (game.getAllPlayer().size() > 2) {
            timeFont.draw(batch, getTimeString(), center.x - 32,
                    center.y + 88);
            if (win) {
                remainFont.draw(batch, finishedPos + ".", center.x - 32, center.y + 128);
            } else {
                remainFont.draw(batch, String.valueOf(remain + 6), center.x - 32, center.y + 128);
            }
        } else {
            timeFont.draw(batch, getTimeString(), center.x - 32,
                    center.y + 88 + 128);
            if (win) {
                remainFont.draw(batch, finishedPos + ".", center.x - 32, center.y + 256);
            } else {
                remainFont.draw(batch, String.valueOf(remain + 6), center.x - 32, center.y + 256);
            }
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void win(int pos) {
        win = true;
        finishedPos = pos;
    }

    public Vector2 getMove() {
        return move;
    }

    public Vector2 getPosition() {
        return new Vector2(playerRectangle.x, playerRectangle.y);
    }

    private void changeSpriteTexture() {
        if (move.x > 0) playerSprite.setTexture(rightTexture);
        else playerSprite.setTexture(leftTexture);
    }

    public int getPos() {
        return pos;
    }

    public void jump() {
        if (!jump) {
            if (playerState == PlayerState.HigherJump) {
                this.move.y = 1000;
            } else {
                this.move.y = 900;
            }

            this.jump = true;
        }
    }

    public void freeze(int duration) {
        this.stateTime = 0;
        this.stateDuration = duration;
        this.playerState = PlayerState.Freezed;

        move.y = 0;

        updatePlayerStateSprite();
    }

    private void enchantPlayer() {
        stateTime = 0;
        stateDuration = new Random().nextInt(10) + 10;

        int s = new Random().nextInt(5);
        if (s == 0) {
            playerState = PlayerState.HigherJump;
        } else if (s == 1) {
            playerState = PlayerState.DoubleSpeed;
            move.x *= 1.5;
        } else if (s == 2) {
            playerState = PlayerState.HalfSpeed;
            move.x /= 2;
        } else if (s == 3) {
            game.getAllPlayer().get(new Random().nextInt(game.getAllPlayer().size())).freeze((int) stateDuration);
        } else if (s == 4) {
            game.world.spawnPattern(new Wall(), game.getAllPlayer().get(new Random().nextInt(game.getAllPlayer().size())).getPos() - World.INIT_VAL + 2);
        }

        updatePlayerStateSprite();
    }

    private void updatePlayerStateSprite() {
        if (playerState == PlayerState.DoubleSpeed) {
            playerStateSprite.setTexture(statesIcons.getDoubleSpeedTexture());
        } else if (playerState == PlayerState.HigherJump) {
            playerStateSprite.setTexture(statesIcons.getHighJumpTexture());
        } else if (playerState == PlayerState.HalfSpeed) {
            playerStateSprite.setTexture(statesIcons.getHalfSpeedTexture());
        } else if (playerState == PlayerState.Freezed) {
            playerStateSprite.setTexture(statesIcons.getFreezeTexture());
        } else {
            playerStateSprite.setTexture(statesIcons.getNullTexture());
        }
    }

    private void disenchantPlayer() {
        playerState = PlayerState.Normal;
        stateTime = 0;

        move.x = (move.x > 0) ? NORMAL_SPEED : -NORMAL_SPEED;

        updatePlayerStateSprite();
    }

    public boolean isWin() {
        return win;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }
}
