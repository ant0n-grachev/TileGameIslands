package com.bmhs.gametitle.game.assets.characters;

import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;

public abstract class Character {

    private float x, y;

    protected WorldTile tile;

    public Character(WorldTile tile, float x, float y) {
        this.tile = tile;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public WorldTile getTile() {
        return tile;
    }

    public void setTile(WorldTile tile) {
        this.tile = tile;
    }

    public void adjustX(float x) {
        this.x += x;
    }

    public void adjustY(float y) {
        this.y += y;
    }
}
