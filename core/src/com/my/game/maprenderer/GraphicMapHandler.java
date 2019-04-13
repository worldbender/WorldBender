package com.my.game.maprenderer;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.my.game.Properties;

public class GraphicMapHandler {
    private TiledMap map;
    private OrthogonalTiledMapRenderer render;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final String BASE_PATH_TO_MAP = "maps/";
    private static final String MAP_FILE_FORMAT = ".tmx";
    public GraphicMapHandler() {
        String startMap = Properties.loadConfigFile("START_MAP");
        map = new TmxMapLoader().load("maps/" + startMap+ ".tmx");
        render = new OrthogonalTiledMapRenderer(this.map);
    }

    public void LoadMap(String mapName){
        String pathToMap = BASE_PATH_TO_MAP + mapName + MAP_FILE_FORMAT;
        map = new TmxMapLoader().load(pathToMap);
        render = new OrthogonalTiledMapRenderer(this.map);
    }

    public TiledMap getMap(){return this.map;}
    public OrthogonalTiledMapRenderer getRender(){return this.render;}
    public int getNumberOfXTiles(){
        return getMap().getProperties().get("width", Integer.class);
    }
    public int getNumberOfYTiles(){
        return getMap().getProperties().get("height", Integer.class);
    }
    public int getMapWidth(){
        return this.getNumberOfXTiles() * TILE_WIDTH;
    }
    public int getMapHeight(){
        return this.getNumberOfYTiles() * TILE_HEIGHT;
    }
}
