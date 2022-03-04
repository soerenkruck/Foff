/*
 * Copyright (c) 2022. Sören Dominik Kruck. krukk Productions.
 */

package com.foff.soerenkruck.world.object;

import com.badlogic.gdx.math.Vector2;
import com.foff.soerenkruck.world.Cell;

public interface Pattern {

    public Cell[][] getPattern(Vector2 startPos);

}
