package server.LogicMap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import java.awt.*;

public class LogicMapHandler {
    private TiledMap map;
    private final int mapWidth = 100;
    private final int mapHeight = 100;
    private final int tileWidth = 32;
    private final int tileHeight = 32;
    private final String BASE_PATH_TO_MAP = "maps/";
    private final String MAP_FILE_FORMAT = ".tmx";
    private ABlock[][] logicMap = new ABlock[mapWidth][mapHeight];
    private EventList eventList;

    public LogicMapHandler() {
        this.map = new TmxMapLoader().load("maps/t9.tmx");
        this.eventList = new EventList();
        this.constructLogicMap();
        this.constructEventObjects();
    }

    /**
     * Function Loads map and creates it's logic representation.
     *
     * @param mapName Name of map without path to folder or format. Just name of map.
     */
    public void LoadMap(String mapName) {
        String pathToMap = BASE_PATH_TO_MAP + mapName + MAP_FILE_FORMAT;
        map = new TmxMapLoader().load(pathToMap);
        constructLogicMap();
        constructEventObjects();
    }
    /**
     * Function creates logic map. It reads map matrix from tmx file and creates a proper block depending on properties of tile.
     */
    public void constructLogicMap() {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) (this.getMap().getLayers().get("CollisionLayer"));
        for (int xTileIndex = 0; xTileIndex < this.getNumerOfXTiles(); xTileIndex++) {
            for (int yTileIndex = 0; yTileIndex < this.getNumerOfYTiles(); yTileIndex++) {
                if (collisionLayer.getCell(xTileIndex, yTileIndex).getTile().getProperties().containsKey("blocked")) {
                    logicMap[xTileIndex][yTileIndex] = new SolidBlock(
                            (xTileIndex * this.tileWidth),
                            (yTileIndex * this.tileHeight),
                            this.tileWidth,
                            this.tileHeight
                    );
                } else {
                    logicMap[xTileIndex][yTileIndex] = new SoftBlock(
                            (xTileIndex * this.tileWidth),
                            (yTileIndex * this.tileHeight),
                            this.tileWidth,
                            this.tileHeight
                    );
                }
            }
        }
    }

    public void constructEventObjects(){
        MapLayer eventLayer = (this.getMap().getLayers().get("EventLayer"));
        MapObjects mapObjects = eventLayer.getObjects();
        RectangleMapObject rectangleMapObject;
        for(MapObject object : mapObjects){
            if(object.getProperties().containsKey("spawn")){
                rectangleMapObject = (RectangleMapObject)(object);
                eventList.add(new EventBlock(
                        (int)rectangleMapObject.getRectangle().getX(),
                        (int)rectangleMapObject.getRectangle().getY(),
                        "spawn",
                        object.getProperties().get("spawn").toString())
                );
            }
            if(object.getProperties().containsKey("enemy")){
                rectangleMapObject = (RectangleMapObject)(object);
                eventList.add(new EventBlock(
                        (int)rectangleMapObject.getRectangle().getX(),
                        (int)rectangleMapObject.getRectangle().getY(),
                        "enemy",
                        object.getProperties().get("enemy").toString())
                );
            }
        }
    }
    public Point getNextPlayerSpawnPoint(){
        return this.eventList.getNextPlayerSpawnPoint();
    }

    public boolean isRectangleCollidesWithMap(Rectangle rec){
        boolean result = false;
        ABlock currentBlock;
        int startObjTileX = ((int)rec.getX()/32) - 3;
        startObjTileX = startObjTileX < 0 ? 0 : startObjTileX;

        int endObjTileX = ((int)rec.getX()/32) + (int)Math.ceil(rec.width/32) + 3;
        endObjTileX = endObjTileX > 100 ? 100 : endObjTileX;

        int startObjTileY = ((int)rec.getY()/32) - 3;
        startObjTileY = startObjTileY < 0 ? 0 : startObjTileY;

        int endObjTileY = ((int)rec.getY()/32) + ((int)Math.ceil(rec.height/32)) + 3;
        endObjTileY = endObjTileY > 100 ? 100 : endObjTileY;

        for(int x = startObjTileX; x < endObjTileX; x++){
            for(int y = startObjTileY; y < endObjTileY; y++){
                currentBlock = this.getCertainTileByTileXY(x,y);
                if(currentBlock.getBLockType() == "Solid"){
                    if(rec.intersects(currentBlock.getRectangle())){
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * @param x Real X position on the Map.
     * @param y Real Y position on the Map;
     * @return Returns block that is on given position.
     */
    public ABlock getCertainTileByPoint(int x, int y) {
        ABlock resultBlock;
        int indexOfBlockInMapMatrixX = (int)Math.floor(((double)x / (double)tileWidth));
        int indexOfBlockInMapMatrixY = (int)Math.floor(((double)y / (double)tileHeight));
        resultBlock = logicMap[indexOfBlockInMapMatrixX][indexOfBlockInMapMatrixY];
        return resultBlock;
    }

    /**
     * @param x X Tile number form left down point
     * @param y Y Tile number form left down point
     * @return Returns block on given position.
     */
    public ABlock getCertainTileByTileXY(int x, int y) {
        ABlock resultBlock;
        resultBlock = logicMap[x][y];
        return resultBlock;
    }

    public TiledMap getMap() {
        return this.map;
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

    public EventList getEventList(){
        return this.eventList;
    }
}
