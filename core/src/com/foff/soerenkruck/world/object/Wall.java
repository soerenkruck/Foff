/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.world.object;

import com.badlogic.gdx.math.Vector2;
import com.foff.soerenkruck.world.Cell;
import com.foff.soerenkruck.world.CellType;
import com.foff.soerenkruck.world.World;

import java.util.Random;

public class Wall implements Pattern {

    @Override
    public Cell[][] getPattern(Vector2 startPos) {

        Cell[][] wall = new Cell[1][new Random().nextInt(2) + 2];

        for (int i = 0; i < wall[0].length; i++) {
            wall[0][i] = new Cell(new Vector2(startPos.x * Cell.SIZE, ((startPos.y + i) + World.min) * Cell.SIZE), CellType.Wall);

        }

        return wall;
    }
}
