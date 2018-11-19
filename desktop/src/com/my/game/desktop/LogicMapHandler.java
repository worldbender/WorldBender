package com.my.game.desktop;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class LogicMapHandler {
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;
    private final int tileWidth = 32;
    private final int tileHeight = 32;
    private final String BASE_PATH_TO_MAP = "maps/";
    private final String MAP_FILE_FORMAT = ".tmx";
    private ABlock[][] logicMap = new ABlock[100][100];

    public LogicMapHandler() {
        map = new TmxMapLoader().load("maps/t9.tmx");
        render = new OrthogonalTiledMapRenderer(this.map);
        constructLogicMap();
    }

    /**
     * Function Loads map and creates it's logic representation.
     *
     * @param mapName Name of map without path to folder or format. Just name of map.
     */
    public void LoadMap(String mapName) {
        String pathToMap = BASE_PATH_TO_MAP + mapName + MAP_FILE_FORMAT;
        map = new TmxMapLoader().load(pathToMap);
        render = new OrthogonalTiledMapRenderer(this.map);
        constructLogicMap();
    }


    /**
     * Function creates logic map. It reads map matrix from tmx file and creates a proper block depending on properties of tile.
     */
    public void constructLogicMap() {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) (this.getMap().getLayers().get(0));
        for (int xTileIndex = 0; xTileIndex < this.getNumerOfXTiles(); xTileIndex++) {
            for (int yTileIndex = 0; yTileIndex < this.getNumerOfYTiles(); yTileIndex++) {
                if (collisionLayer.getCell(xTileIndex, yTileIndex).getTile().getProperties().containsKey("blocked")) {
                    logicMap[xTileIndex][yTileIndex] = new SolidBlock(
                            xTileIndex * this.tileWidth,
                            (yTileIndex * this.tileHeight) + this.tileHeight,
                            this.tileWidth,
                            this.tileHeight
                    );
                    System.out.println(xTileIndex + "  " + yTileIndex + " " + logicMap[xTileIndex][yTileIndex].rectangle.x + " " + logicMap[xTileIndex][yTileIndex].rectangle.y);
                } else {
                    logicMap[xTileIndex][yTileIndex] = new SoftBlock(
                            xTileIndex * this.tileWidth,
                            (yTileIndex * this.tileHeight) + this.tileHeight,
                            this.tileWidth,
                            this.tileHeight
                    );
                }
            }
        }
    }

    /**
     * @param x Real X position on the Map.
     * @param y Real Y position on the Map;
     * @return Returns block that is on given position.
     */
    public ABlock getCertainTileByPoint(int x, int y) {
        ABlock resultBlock;
        int indexOfBlockInMapMatrixX = x / tileWidth;
        int indexOfBlockInMapMatrixY = y / tileHeight;
//        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) (this.getMap().getLayers().get(0));
//        Iterator iter = collisionLayer.getCell(indexOfBlockInMapMatrixX, indexOfBlockInMapMatrixY).getTile().getProperties().getKeys();
//        while (iter.hasNext()) {
//            System.out.println(iter.next().toString());
//        }
        resultBlock = logicMap[indexOfBlockInMapMatrixX][indexOfBlockInMapMatrixY];
        return resultBlock;
    }

    public TiledMap getMap() {
        return this.map;
    }

    public OrthogonalTiledMapRenderer getRender() {
        return this.render;
    }

    public int getNumerOfXTiles() {
        return getMap().getProperties().get("width", Integer.class);
    }

    public int getNumerOfYTiles() {
        return getMap().getProperties().get("height", Integer.class);
    }

    public int getMapWidth() {
        return this.getNumerOfXTiles() * tileWidth;
    }

    public int getMapHeight() {
        return this.getNumerOfYTiles() * tileHeight;
    }
}
