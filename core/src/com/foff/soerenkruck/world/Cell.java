package com.foff.soerenkruck.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cell {

    public static final int SIZE = 48;

    private Sprite sprite;

    private Rectangle rectangle;

    private boolean isSolid;

    private CellType type;

    public Cell(Vector2 pos, CellType type) {
        this.type = type;

        isSolid = (type == CellType.Gras || type == CellType.Dirt ||type == CellType.Wall);

        if (type == CellType.Gras)
            this.sprite = new Sprite(new Texture("gras.png"));
        else if (type == CellType.Dirt)
            this.sprite = new Sprite(new Texture("dirt.png"));
        else if (type == CellType.Item)
            this.sprite = new Sprite(new Texture("item_1.png"));
        else if (type == CellType.Wall)
            this.sprite = new Sprite(new Texture("stone_mesh.png"));
        else this.sprite = new Sprite(new Texture("null.png"));

        this.rectangle = new Rectangle(pos.x, pos.y, SIZE, SIZE);
        sprite.setBounds(pos.x, pos.y, SIZE, SIZE);
    }

    public CellType getType() {
        return type;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public Vector2 getPos() {
        return new Vector2(rectangle.x, rectangle.y);
    }
    public Rectangle getRectangle() {
        return rectangle;
    }

    public Sprite getSprite() {
        return sprite;
    }
}

