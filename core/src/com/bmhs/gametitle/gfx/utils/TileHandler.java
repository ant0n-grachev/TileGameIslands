package com.bmhs.gametitle.gfx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import com.bmhs.gametitle.gfx.assets.tiles.Tile;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;

public class TileHandler {

    public static TileHandler tileHandler = null;

    private int spriteSheetHeight, spriteSheetWidth, spriteOnSheetHeight, spriteOnSheetWidth;

    private final String primaryColorSheetPath = "primaryColorSheet.png";
    private final String grassSpriteSheetPath = "grassSpriteSheet.png";

    private Array<WorldTile> worldTileArray;

    private TileHandler() {
        spriteOnSheetHeight = Tile.ON_SCREEN_DEFAULT_HEIGHT;
        spriteOnSheetWidth = Tile.ON_SCREEN_DEFAULT_WIDTH;
        spriteSheetHeight = 640;
        spriteSheetWidth = 640;

        worldTileArray = new Array<>();

        worldTileArray.add(new WorldTile(new TextureRegion(new Texture(primaryColorSheetPath), 0, 0, spriteOnSheetWidth, spriteOnSheetHeight), 0, "transparent"));

        createWorldTiles(primaryColorSheetPath, "primary color", worldTileArray);
        createWorldTiles(grassSpriteSheetPath, "grass", worldTileArray);
    }

    public static TileHandler getTileHandler() {
        if(tileHandler == null) {
            tileHandler = new TileHandler();
        }
        return tileHandler;
    }

    private void createWorldTiles(String path, String imageNamePrefix, Array<WorldTile> tileArray) {
        Texture sheet = new Texture(Gdx.files.internal(path));
        Pixmap pixmap = new Pixmap(Gdx.files.internal(path));

        int count = 0;

        for(int r = 0; r < sheet.getHeight(); r+=spriteOnSheetHeight) {
            for(int c = 0; c < sheet.getWidth(); c+=spriteOnSheetWidth) {
                boolean solidFound = false;
                for(int pixelR = 0; pixelR < spriteOnSheetHeight; pixelR++) {
                    for(int pixelC = 0; pixelC < spriteOnSheetWidth; pixelC++) {
                        if(pixmap.getPixel(c+pixelC, r+pixelR) != 0) {
                            solidFound = true;
                        }
                    }
                }
                if(solidFound) {
                    tileArray.add(new WorldTile(new TextureRegion(sheet, c, r, spriteOnSheetWidth, spriteOnSheetHeight), count++, (imageNamePrefix+"_"+count)));
                }
            }
        }
    }

    public Array<TextureRegion> loadFramesFromPath(String path, int startRowInc, int startColInc, int endRowExl, int endColExl) {
        startRowInc*=64;
        startColInc*=64;
        endRowExl*=64;
        endColExl*=64;

        Array<TextureRegion> returnArray = new Array<>();
        Texture sheet = new Texture(Gdx.files.internal(path));
        Pixmap pixmap = new Pixmap(Gdx.files.internal(path));

        int count = 0;

        for(int r = startRowInc; r < endRowExl; r+=spriteOnSheetHeight) {
            for(int c = startColInc; c < endColExl; c+=spriteOnSheetWidth) {
                //getPixel(x,y)
                if(pixmap.getPixel(c+32, r+32) != 0) {
                    returnArray.add(new TextureRegion(sheet, c, r, spriteOnSheetWidth, spriteOnSheetHeight));
                }
            }
        }
        return returnArray;
    }

    public Array<WorldTile> getWorldTileArray() {
        return worldTileArray;
    }
}