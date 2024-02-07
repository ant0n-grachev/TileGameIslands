package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.FileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int[][] worldIntMap;

    private int seedColor, grass, sand, water;

    Random rand = new Random();

    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

//        Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
//
//        worldIntMap[(int) mapSeed.y][(int) mapSeed.x] = 4;
//
//        for (int r = 0; r < worldIntMap.length; r++) {
//            for (int c = 0; c < worldIntMap[r].length; c++) {
//                worldIntMap[r][c] = 19;
//                Vector2 tempVector = new Vector2(c, r);
//                if (tempVector.dst(mapSeed) < 10) {
//                    worldIntMap[r][c] = 2;
//                }
//            }
//        }

        seedColor = 2;
        grass = 67;
        sand = 29;
        water = 84;

        for (int[] i : worldIntMap) {
            Arrays.fill(i, water);
        }

        seedIslands(10);
        buildIslands();

        generateWorldTextFile();

        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
    }


    private void buildIslands() {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == seedColor) {
                    Vector2 origin = new Vector2(r, c);
                    buildIsland(r, c, origin);
                    worldIntMap[r][c] = seedColor;
                }
            }
        }
//        for (int r = 0; r < worldIntMap.length; r++) {
//            for (int c = 0; c < worldIntMap[r].length; c++) {
//                if (worldIntMap[r][c] == water) {
//
//                }
//            }
//        }
    }

    private void buildIsland(int r, int c, Vector2 origin) {
        worldIntMap[r][c] = sand;
        Vector2 currentDot = new Vector2(r, c);
        int dst = (int) origin.dst(currentDot);
        if (dst < rand.nextInt(40) + 40 && r > 1 && c > 1 && r < worldIntMap.length - 1 && c < worldIntMap[0].length - 1) {
            int n = rand.nextInt(8);
            switch (n) {
                case 0:
                    buildIsland(r - 1, c - 1, origin);
                    break;
                case 1:
                    buildIsland(r - 1, c, origin);
                    break;
                case 2:
                    buildIsland(r - 1, c + 1, origin);
                    break;
                case 3:
                    buildIsland(r, c + 1, origin);
                    break;
                case 4:
                    buildIsland(r + 1, c + 1, origin);
                    break;
                case 5:
                    buildIsland(r + 1, c, origin);
                    break;
                case 6:
                    buildIsland(r + 1, c - 1, origin);
                    break;
                case 7:
                    buildIsland(r, c - 1, origin);
                    break;
            }
        }
    }

    private void seedIslands(int num) {
        for (int i = 0; i < num; i++) {
            int rSeed = MathUtils.random(worldIntMap.length - 1);
            int cSeed = MathUtils.random(worldIntMap[0].length);
            worldIntMap[rSeed][cSeed] = seedColor;
        }
    }

    private void searchAndExpand(int radius) {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == seedColor) {
                    for (int subRow = r - radius; subRow <= r + radius; subRow++) {
                        for (int subCol = c - radius; subCol <= c + radius; subCol++) {
                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length - 1 && subCol <= worldIntMap[0].length - 1 && worldIntMap[subRow][subCol] != seedColor) {
                                worldIntMap[subRow][subCol] = 3;
                            }
                        }
                    }
                }
            }
        }
    }

    private void searchAndExpand(int radius, int numToFind, int numToWrite, double probability) {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == numToFind) {
                    for (int subRow = r - radius; subRow <= r + radius; subRow++) {
                        for (int subCol = c - radius; subCol <= c + radius; subCol++) {
                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length - 1 && subCol <= worldIntMap[0].length - 1 && worldIntMap[subRow][subCol] != numToFind) {
                                if (Math.random() < probability)
                                    worldIntMap[subRow][subCol] = numToWrite;
                            }
                        }
                    }
                }
            }
        }
    }

    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }

    public void randomize() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
        }
    }

    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }

    private void generateWorldTextFile() {
        FileHandle file = Gdx.files.local("assets/worlds/world.txt");
        file.writeString(getWorld3DArrayToString(), false);
    }
}
