package server.logicmap;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import server.Properties;
import server.connection.GameController;
import server.pickups.APickup;
import server.pickups.PickupFactory;
import server.pickups.PickupList;

import java.awt.*;

public class LogicMapHandler {
    private TiledMap map;
    private static final int MAP_WIDTH = 100;
    private static final int MAP_HEIGHT = 100;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final String BASE_PATH_TO_MAP = "maps/";
    private static final String MAP_FILE_FORMAT = ".tmx";
    private ABlock[][] logicMap = new ABlock[MAP_WIDTH][MAP_HEIGHT];
    private PickupList pickupList;
    private EventList eventList;
    private GameController gameController;
    private static final String ENEMY = "enemy";
    private static final String SPAWN = "spawn";

    public LogicMapHandler(GameController gameController) {
        String startMap = Properties.loadConfigFile("START_MAP");
        this.map = new TmxMapLoader().load("maps/" + startMap + ".tmx");
        this.eventList = new EventList(gameController);
        this.pickupList = gameController.pickupList;
        this.gameController = gameController;
        this.constructLogicMap();
        this.constructEventObjects();
    }

    /**
     * Function Loads map and creates it's logic representation.
     *
     * @param mapName Name of map without path to folder or format. Just name of map.
     */
    public void loadMap(String mapName) {
        String pathToMap = BASE_PATH_TO_MAP + mapName + MAP_FILE_FORMAT;
        map = new TmxMapLoader().load(pathToMap);
        this.eventList = new EventList(this.gameController);
        constructLogicMap();
        constructEventObjects();
    }
    /**
     * Function creates logic map. It reads map matrix from tmx file and creates a proper block depending on properties of tile.
     */
    public void constructLogicMap() {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) (this.getMap().getLayers().get("CollisionLayer"));
        TiledMapTileLayer collisionLayer2 = (TiledMapTileLayer) (this.getMap().getLayers().get("CollisionLayer2"));
        for (int xTileIndex = 0; xTileIndex < this.getNumerOfXTiles(); xTileIndex++) {
            for (int yTileIndex = 0; yTileIndex < this.getNumerOfYTiles(); yTileIndex++) {
                if (collisionLayer.getCell(xTileIndex, yTileIndex).getTile().getProperties().containsKey("blocked") ||
                        collisionLayer2.getCell(xTileIndex, yTileIndex).getTile().getProperties().containsKey("blocked")) {
                    logicMap[xTileIndex][yTileIndex] = new SolidBlock(
                            (xTileIndex * this.TILE_WIDTH),
                            (yTileIndex * this.TILE_HEIGHT),
                            this.TILE_WIDTH,
                            this.TILE_HEIGHT
                    );
                } else {
                    logicMap[xTileIndex][yTileIndex] = new SoftBlock(
                            (xTileIndex * this.TILE_WIDTH),
                            (yTileIndex * this.TILE_HEIGHT),
                            this.TILE_WIDTH,
                            this.TILE_HEIGHT
                    );
                }
                if(collisionLayer.getCell(xTileIndex, yTileIndex).getTile().getProperties().containsKey("door")){
                    logicMap[xTileIndex][yTileIndex] = new SolidBlock(
                            (xTileIndex * this.TILE_WIDTH),
                            (yTileIndex * this.TILE_HEIGHT),
                            this.TILE_WIDTH,
                            this.TILE_HEIGHT,
                            true
                    );
                    if(collisionLayer.getCell(xTileIndex, yTileIndex).getTile().getProperties().containsKey("portal")){
                        logicMap[xTileIndex][yTileIndex].setPortal(true);
                        logicMap[xTileIndex][yTileIndex].setPortalDirection(collisionLayer.getCell(xTileIndex, yTileIndex).getTile().getProperties().get("portal").toString());
                    }
                }
            }
        }
    }

    public void constructEventObjects(){
        MapLayer eventLayer = (this.getMap().getLayers().get("EventLayer"));
        MapObjects mapObjects = eventLayer.getObjects();
        RectangleMapObject rectangleMapObject;
        for(MapObject object : mapObjects){
            if(object.getProperties().containsKey("active")){
                if(object.getProperties().containsKey("warp")){
                    eventList.setNextMap(object.getProperties().get("warp").toString());
                }

            } else {
                if(object.getProperties().containsKey(SPAWN)){
                    rectangleMapObject = (RectangleMapObject)(object);
                    eventList.addPassiveEvent(new EventBlock(
                            (int)rectangleMapObject.getRectangle().getX(),
                            (int)rectangleMapObject.getRectangle().getY(),
                            SPAWN,
                            object.getProperties().get(SPAWN).toString())
                    );
                }
                if(object.getProperties().containsKey(ENEMY)){
                    rectangleMapObject = (RectangleMapObject)(object);
                    eventList.addPassiveEvent(new EventBlock(
                            (int)rectangleMapObject.getRectangle().getX(),
                            (int)rectangleMapObject.getRectangle().getY(),
                            ENEMY,
                            object.getProperties().get(ENEMY).toString())
                    );
                }
            }
        }
    }

    public void openDoors(){
        for (int xTileIndex = 0; xTileIndex < this.getNumerOfXTiles(); xTileIndex++) {
            for (int yTileIndex = 0; yTileIndex < this.getNumerOfYTiles(); yTileIndex++) {
                if(logicMap[xTileIndex][yTileIndex].isDoor()){
                    logicMap[xTileIndex][yTileIndex].setBlockType("Soft");
                    if(logicMap[xTileIndex][yTileIndex].isPortal()){
                        APickup pickup = PickupFactory.createPickup(
                                (xTileIndex * this.TILE_WIDTH),
                                (yTileIndex * this.TILE_HEIGHT),
                                "InvisibleWarp"
                        );
                        pickup.setDirection(logicMap[xTileIndex][yTileIndex].getPortalDirection());
                        pickup.setBlocked(false);
                        this.pickupList.addPickup(pickup);
                    }
                }
            }
        }
    }

    public Point getNextPlayerSpawnPoint(){
        return this.eventList.getNextPlayerSpawnPoint();
    }

    public boolean isRectangleCollidesWithMap(Rectangle rec){
        boolean result = false;
        int numerOfXTiles = this.map.getProperties().get("width", Integer.class);
        int numerOfYTiles = this.map.getProperties().get("height", Integer.class);
        ABlock currentBlock;

        int startObjTileX = ((int)rec.getX()/32) - 3;
        startObjTileX = startObjTileX < 0 ? 0 : startObjTileX;

        int endObjTileX = ((int)rec.getX()/32) + (int)Math.ceil((double)rec.width/32) + 3;
        endObjTileX = endObjTileX > numerOfXTiles ? numerOfXTiles : endObjTileX;

        int startObjTileY = ((int)rec.getY()/32) - 3;
        startObjTileY = startObjTileY < 0 ? 0 : startObjTileY;

        int endObjTileY = ((int)rec.getY()/32) + ((int)Math.ceil((double)rec.height/32)) + 3;
        endObjTileY = endObjTileY > numerOfYTiles ? numerOfYTiles : endObjTileY;

        for(int x = startObjTileX; x < endObjTileX; x++){
            for(int y = startObjTileY; y < endObjTileY; y++){
                currentBlock = this.getCertainTileByTileXY(x,y);
                if(currentBlock.getBLockType().equals("Solid") && rec.intersects(currentBlock.getRectangle())){
                    result = true;
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
        int indexOfBlockInMapMatrixX = (int)Math.floor(((double)x / (double) TILE_WIDTH));
        int indexOfBlockInMapMatrixY = (int)Math.floor(((double)y / (double) TILE_HEIGHT));
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
        return this.getNumerOfXTiles() * TILE_WIDTH;
    }

    public int getMapHeight() {
        return this.getNumerOfYTiles() * TILE_HEIGHT;
    }

    public EventList getEventList(){
        return this.eventList;
    }
}
