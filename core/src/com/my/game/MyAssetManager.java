package com.my.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;


public class MyAssetManager {

    public static final AssetManager manager = new AssetManager();

    //playerTexture
    public final static String down = "isaac/downIsaac.png";
    public final static String left = "isaac/leftWalkIsaac.png";
    public final static String right = "isaac/rightWalkIsaac.png";
    public final static String up = "isaac/upIsaac.png";

    //opponents
    public final static String nietzsche 		= "opponents/nietzsche.png";
    public final static String poe 		= "opponents/poe.png";
    public final static String schopen 		= "opponents/schopen.png";

    //pickups
    public final static String hp 		= "pickups/hp.png";
    public final static String InnerEye 		= "pickups/InnerEye.png";
    public final static String SadOnion 		= "pickups/SadOnion.png";

    //sounds
    public final static String hpup 		= "sounds/hpup.wav";
    public final static String meow 		= "sounds/meow.mp3";
    public final static String sound1 		= "sounds/sound1.mp3";

    //skins
    public final static String font_export 		= "skin/font-export.fnt";
    public final static String font_medium_export 		= "skin/font-medium-export.fnt";
    public final static String font_small_export 		= "skin/font-small-export.fnt";
    private final static String font_title_export   ="skin/font-title-export.fnt";
    private final static String sgx_ui_atlas 		= "skin/sgx-ui.atlas";
    private final static String sgx_ui_json 		= "skin/sgx-ui.json";
    private final static String sgx_ui_png 		= "skin/sgx-ui.png";




    public static void loadPlayerTexture(){
        manager.load(down, Texture.class);
        manager.load(left, Texture.class);
        manager.load(right, Texture.class);
        manager.load(up, Texture.class);
    }

    public static void loadOpponents(){
        manager.load(nietzsche, Texture.class);
        manager.load(poe, Texture.class);
        manager.load(schopen, Texture.class);
    }

    public static void loadPickups(){
        manager.load(hpup, Texture.class);
        manager.load(InnerEye, Texture.class);
        manager.load(SadOnion, Texture.class);
    }

    public static void loadMusic(){
        manager.load(hpup, Sound.class);
        manager.load(meow, Sound.class);
        manager.load(sound1, Sound.class);
    }

    public static void loadSkins(){
    }

    public static void loadAllAssets(){
        manager.load(down, Texture.class);
        manager.load(left, Texture.class);
        manager.load(right, Texture.class);
        manager.load(up, Texture.class);
        manager.load(nietzsche, Texture.class);
        manager.load(poe, Texture.class);
        manager.load(schopen, Texture.class);
        manager.load(hp, Texture.class);
        manager.load(InnerEye, Texture.class);
        manager.load(SadOnion, Texture.class);
        manager.load(hpup, Sound.class);
        manager.load(meow, Sound.class);
        manager.load(sound1, Sound.class);
    }


    public static void dispose(){
        manager.dispose();
    }

}
