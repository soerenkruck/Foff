package com.foff.soerenkruck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.foff.soerenkruck.controller.Player;
import com.foff.soerenkruck.controller.StatesIcons;
import com.foff.soerenkruck.screen.MainMenu;
import com.foff.soerenkruck.ui.UserInterface;
import com.foff.soerenkruck.world.Cell;
import com.foff.soerenkruck.world.World;
import com.foff.soerenkruck.world.object.Wall;

import java.util.ArrayList;
import java.util.Random;

public class Game implements Screen {
	SpriteBatch batch;

	ArrayList<Player> allPlayer;
	private int numberOfPlayer;
	final float OPPOSITE_PLAYER_ALPHA = 0.4f;
	private StatesIcons statesIcons;
	int finished = 1;

	public World world;
	boolean nightMode;

	private UserInterface userInterface;

	private int numberOfStartItems = 4;

	OrthographicCamera[] camera;
	FitViewport[] viewport;

	private BitmapFont font;

	private int halfScreenWidth, halfHeight;

	private float globalSeconds;

	public Game(int numberOfPlayers, boolean nightMode) {
		this.nightMode = nightMode;
		this.numberOfPlayer = numberOfPlayers;

		userInterface = new UserInterface("");
		userInterface.setFontColor(Color.WHITE);
	}

	private void init() {
		world = new World();
		world.generateItemAtPosition(10);
		for (int i = 0; i < numberOfStartItems; i++) {
			world.generateRandomItem();
		}

		finished = 1;

		allPlayer = new ArrayList<>();
		for (int i = 0; i < numberOfPlayer; i++) {
			allPlayer.add(new Player(1200, this));
		}

		globalSeconds = 0;
	}

	public ArrayList<Player> getAllPlayer() {
		return allPlayer;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		init();
		halfScreenWidth = Gdx.graphics.getWidth() / 2;
		halfHeight = Gdx.graphics.getHeight() / 2;

		camera = new OrthographicCamera[numberOfPlayer];
		viewport = new FitViewport[numberOfPlayer];

		// Init of Camerase and Viewports
		if (numberOfPlayer == 1) {
			camera[0] = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			viewport[0] = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera[0]);
		} else {
			if (numberOfPlayer > 2) {
				camera[0] = new OrthographicCamera(halfScreenWidth, halfHeight);
				camera[1] = new OrthographicCamera(halfScreenWidth, halfHeight);

				viewport[0] = new FitViewport(halfScreenWidth, halfHeight, camera[0]);
				viewport[1] = new FitViewport(halfScreenWidth, halfHeight, camera[1]);

				if (numberOfPlayer == 3) {
					camera[2] = new OrthographicCamera(Gdx.graphics.getWidth(), halfHeight);
					viewport[2] = new FitViewport(Gdx.graphics.getWidth(), halfHeight);
				} else {
					camera[2] = new OrthographicCamera(halfScreenWidth, halfHeight);
					viewport[2] = new FitViewport(halfScreenWidth, halfHeight);
					camera[3] = new OrthographicCamera(halfScreenWidth, halfHeight);
					viewport[3] = new FitViewport(halfScreenWidth, halfHeight);
				}
			} else {
				camera[0] = new OrthographicCamera(halfScreenWidth, Gdx.graphics.getHeight());
				camera[1] = new OrthographicCamera(halfScreenWidth, Gdx.graphics.getHeight());

				viewport[0] = new FitViewport(halfScreenWidth, Gdx.graphics.getHeight(), camera[0]);
				viewport[1] = new FitViewport(halfScreenWidth, Gdx.graphics.getHeight(), camera[1]);
			}

		}

		font = new BitmapFont();
		font.getData().setScale(4);

		nightMode = false;

		statesIcons = new StatesIcons();
	}

	@Override
	public void render(float delta) {

		globalSeconds += delta; // Wird aktuell nicht benötigt

		input(delta);

		// Rendering
		if (nightMode) {
			Gdx.gl.glClearColor(0.05f, 0.08f, .15f, 1);
		} else {
			Gdx.gl.glClearColor(0.3f, 0.5f, 1, 1);
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int min = World.RANGE;
		int max = 0;

		for (int i = 0; i < allPlayer.size(); i++) {
			Player targetPlayer = allPlayer.get(i);
			int pos = Math.round(targetPlayer.getPosition().x / Cell.SIZE);

			if (pos < min) min = pos;
			if (pos > max) max = pos;

			int remain = Math.round(((Cell.SIZE * World.RANGE) - targetPlayer.getPosition().x) / Cell.SIZE);
			if (remain == -6 && !targetPlayer.isWin()) targetPlayer.win(finished++);

			targetPlayer.increaseTime(delta);

			OrthographicCamera currentCamera = camera[i];
			FitViewport currentViewport = viewport[i];

			if (numberOfPlayer > 2) {
				currentCamera.position.set(new Vector3(targetPlayer.getPosition().x, targetPlayer.getPosition().y, 0));
			} else {
				currentCamera.position.set(new Vector3(targetPlayer.getPosition().x, targetPlayer.getPosition().y + 196, 0));
			}

			currentCamera.update();

			// Viewports updating
			if (i == 0) {
				if (numberOfPlayer > 1) {
					if (numberOfPlayer > 2) {
						currentViewport.update(halfScreenWidth, halfHeight);
						currentViewport.setScreenY(halfHeight);
					} else {
						currentViewport.update(halfScreenWidth, Gdx.graphics.getHeight());
					}
				} else {
					currentViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
			}
			if (i == 1) {
				if (numberOfPlayer > 2) {
					currentViewport.update(halfScreenWidth, halfHeight);
					currentViewport.setScreenPosition(halfScreenWidth, halfHeight);
				} else {
					currentViewport.update(halfScreenWidth, Gdx.graphics.getHeight());
					currentViewport.setScreenX(halfScreenWidth);
				}
			}
			if (i == 2) {
				if (numberOfPlayer > 3) {
					currentViewport.update(halfScreenWidth, halfHeight);
				} else {
					currentViewport.update(Gdx.graphics.getWidth(), halfHeight);
				}
			}
			if (i == 3) {
				currentViewport.update(halfScreenWidth, halfHeight);
				currentViewport.setScreenX(halfScreenWidth);
			}
			currentViewport.apply();

			batch.setProjectionMatrix(currentCamera.combined);

			batch.begin();

			world.makeClouds(batch, delta);
			world.drawWorld(batch, currentCamera);

			for (Player n: allPlayer) {
				if (n != targetPlayer) n.getPlayerSprite().draw(batch, OPPOSITE_PLAYER_ALPHA);
			}
			targetPlayer.draw(batch, remain);

			targetPlayer.drawPlayerState(batch, currentCamera);

			batch.end();
		}

		if (new Random().nextInt(4000) == 1109) {
			world.generateItemInRange(min, max);
		}
	}

	private void input(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu("Foff0"));
		if (allPlayerFinished()) {
			if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
				init();
			}
		}

		// Player 1
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			allPlayer.get(0).jump();
			allPlayer.get(0).move(delta, world, false);
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			allPlayer.get(0).move(delta, world, true);
		} else {
			allPlayer.get(0).move(delta, world, false);
		}

		if (numberOfPlayer > 1) {
			// Control Player right
			if (Gdx.input.isKeyPressed(Input.Keys.O)) {
				allPlayer.get(1).jump();
				allPlayer.get(1).move(delta, world, false);
			} else if (Gdx.input.isKeyPressed(Input.Keys.L)) {
				allPlayer.get(1).move(delta, world, true);
			} else {
				allPlayer.get(1).move(delta, world, false);
			}

			if (numberOfPlayer > 2) {
				if (Gdx.input.isKeyPressed(Input.Keys.E)) {
					allPlayer.get(2).jump();
					allPlayer.get(2).move(delta, world, false);
				} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
					allPlayer.get(2).move(delta, world, true);
				} else {
					allPlayer.get(2).move(delta, world, false);
				}

				if (numberOfPlayer > 3) {
					if (Gdx.input.isKeyPressed(Input.Keys.I)) {
						allPlayer.get(3).jump();
						allPlayer.get(3).move(delta, world, false);
					} else if (Gdx.input.isKeyPressed(Input.Keys.K)) {
						allPlayer.get(3).move(delta, world, true);
					} else {
						allPlayer.get(3).move(delta, world, false);
					}
				}
			}
		}

	}

	private boolean allPlayerFinished() {
		boolean t = true;
		for (Player p: allPlayer) {
			p.isWin();
		}

		return t;
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
	public void dispose () {
		batch.dispose();
	}
}
