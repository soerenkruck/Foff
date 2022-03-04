package com.foff.soerenkruck.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.foff.soerenkruck.mathematics.FastNoise;
import com.foff.soerenkruck.world.object.Pattern;

import java.util.ArrayList;
import java.util.Random;

public class World {

    private ArrayList<Cell[]> Zellen;
    private ArrayList<Item> items;
    private ArrayList<Cloud> clouds;

    public final int MAX_HEIGHT = 32;
    public final int MAX_WORLD_HEIGHT = 32;

    public static final int RANGE = 300;

    public static final int INIT_VAL = 10;
    public static final int FINISH_PLATFORM_LENGTH = 10;

    public static final int min = -16;

    public final static int CLOUDS_NUMBER = 40;

    public World() {
        Zellen = new ArrayList<>();
        items = new ArrayList<>();

        clouds = new ArrayList<>();
        for (int i = 0; i < CLOUDS_NUMBER; i++) {
            clouds.add(new Cloud(new Random().nextInt(RANGE * Cell.SIZE)));
        }

        FastNoise lowNoise = new FastNoise(new Random().nextInt(100000));
        FastNoise fastNoise = new FastNoise(new Random().nextInt(100000));

        for (int x = -INIT_VAL; x < RANGE + FINISH_PLATFORM_LENGTH; x++) {

            int r = Math.round(lowNoise.GetPerlin(x * 8, 1) * MAX_HEIGHT / 2) + Math.round(fastNoise.GetPerlin(x * 16, 1) * MAX_HEIGHT / 4) + 8;
            if (x < 0) r = 32;

            if (x > RANGE)
                r = getFloorHeight(Zellen.get(RANGE + INIT_VAL)) + min;

            Cell cells[] = new Cell[MAX_WORLD_HEIGHT - min];

            for (int i = min; i < MAX_WORLD_HEIGHT; i++) {
                if (i == r - 1) {
                    cells[i - min] = new Cell(new Vector2(x * Cell.SIZE, i * Cell.SIZE), CellType.Gras);
                } else if (i < r) {
                    cells[i - min] = new Cell(new Vector2(x * Cell.SIZE, i * Cell.SIZE), CellType.Dirt);
                } else {
                    cells[i - min] = new Cell(new Vector2(x * Cell.SIZE, i * Cell.SIZE), CellType.NULL);
                }

            }

            Zellen.add(cells);
        }

    }

    public void spawnPattern(Pattern pattern, int xPos) {
        int tH = getFloorHeight(xPos);
        Cell cells[][] = pattern.getPattern(new Vector2(xPos, tH));

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Zellen.get(i + xPos + INIT_VAL)[j + tH] = cells[i][j];
            }
        }

        int i = 0;
    }

    private int getFloorHeight(Cell[] cells) {
        int i = 0;
        for (int j = 0; j < cells.length; j++) {
            if (cells[j] != null) {
                if (cells[j].isSolid()) {
                    i++;
                }
            } else {
                break;
            }
        }
        return i;
    }

    public int getFloorHeight(int pos) {
        return getFloorHeight(Zellen.get(pos + INIT_VAL));
    }

    public void drawWorld(SpriteBatch batch, Camera camera) {
        Rectangle renderRectangle = new Rectangle(camera.position.x - (camera.viewportWidth / 2), camera.position.y - (camera.viewportHeight / 2), camera.viewportWidth, camera.viewportHeight);

        for (Cell[] cellList: Zellen) {
            for (Cell c: cellList) {
                if (c != null) {
                    if (c.getRectangle().overlaps(renderRectangle) && c.getType() != CellType.NULL) {
                        if (c.getSprite() != null) c.getSprite().draw(batch);
                    }
                }
            }
        }

        for (Item i: items) {
            i.getSprite().draw(batch);
        }
    }

    public void makeClouds(SpriteBatch spriteBatch, float delta) {
        for (int i = 0; i < clouds.size(); i++) {
            Cloud c = clouds.get(i);
            c.draw(spriteBatch, delta);
            if (c.getX() < -128) {
                clouds.remove(i--);
            }
        }

        for (int i = 0; i < CLOUDS_NUMBER - clouds.size(); i++) {
            clouds.add(new Cloud(Cloud.UNSPECIFIC));
        }

    }

    public void generateRandomItem() {
        if (items.size() < RANGE / 24) generateItemAtPosition(new Random().nextInt(RANGE));
    }

    public void generateItemAtPosition(int x) {
        Cell[] t = Zellen.get(x + INIT_VAL);

        int height = getFloorHeight(t);
        Cell cellUnderneath = t[height - 1];
        items.add(new Item(new Vector2(cellUnderneath.getPos().x, cellUnderneath.getPos().y + Cell.SIZE + Cell.SIZE), CellType.Item));
    }

    public void generateItemInRange(int min, int max) {
        if (min != max) generateItemAtPosition(new Random().nextInt(max - min) + min);
    }

    public boolean hits(Rectangle r) {
        for (Cell[] cellList: Zellen) {
            for (Cell c : cellList) {
                if (c == null) return false;
                if (c.isSolid()) {
                    if (c.getRectangle().overlaps(r)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean hitsItem(Rectangle r) {
        for (Item i: items) {
            if (i.getRectangle().overlaps(r)) {
                items.remove(i);
                return true;
            }
        }

        return false;
    }
}
