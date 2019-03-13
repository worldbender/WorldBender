package com.my.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class MyAssetManager {

    public static final AssetManager manager = new AssetManager();

    //bars
    public final static String healthBar = "bars/healthBar.png";

    //bullets
    public final static String granat = "bullets/granat.png";
    public final static String spectralTear = "bullets/spectralTear.png";

    //playerTexture
    public final static String down = "isaac/downIsaacHeadless2.png";
    public final static String left = "isaac/leftWalkIsaacHeadless.png";
    public final static String right = "isaac/rightWalkIsaacHeadless2.png";
    public final static String up = "isaac/upIsaacHeadless2.png";

    //opponents
    public final static String nietzsche = "opponents/nietzsche.png";
    public final static String poe = "opponents/poe.png";
    public final static String schopen = "opponents/schopen.png";

    //pickups
    public final static String hp = "pickups/hp.png";
    public final static String InnerEye = "pickups/InnerEye.png";
    public final static String SadOnion = "pickups/SadOnion.png";
    public final static String warp = "pickups/warp.png";

    //screenImages
    public final static String tree = "screenImages/tree.jpg";

    //sounds
    public final static String hpup = "sounds/hpup.wav";
    public final static String meow = "sounds/meow.mp3";

    //fonts
    public final static String font_export = "skin/font-export.fnt";
    public final static String font_medium_export = "skin/font-medium-export.fnt";
    public final static String font_small_export = "skin/font-small-export.fnt";
    public final static String font_title_export ="skin/font-title-export.fnt";

    //skins
    public final static String sgx_ui_atlas = "skin/sgx-ui.atlas";
    public final static String sgx_ui_json = "skin/sgx-ui.json";
    public final static String sgx_ui_png = "skin/sgx-ui.png";


    public static void loadBullets(){
        manager.load(granat, Texture.class);
        manager.load(spectralTear, Texture.class);
    }

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
        manager.load(hp, Texture.class);
        manager.load(InnerEye, Texture.class);
        manager.load(SadOnion, Texture.class);
        manager.load(warp, Texture.class);
    }

    public static void loadMusic(){
        manager.load(hpup, Sound.class);
        manager.load(meow, Sound.class);
    }


    public static void loadBars(){
        manager.load(healthBar, Texture.class);
    }

    public static void loadSkins(){
        manager.load(sgx_ui_json, Skin.class);
    }

    public static void loadFonts(){
        manager.load(font_export, BitmapFont.class);
        manager.load(font_medium_export, BitmapFont.class);
        manager.load(font_small_export, BitmapFont.class);
        manager.load(font_title_export, BitmapFont.class);
    }

    public static void loadScreenImages(){
        manager.load(tree, Texture.class);
    }

    public static void loadAllAssets(){
        loadBullets();
        loadMusic();
        loadOpponents();
        loadPickups();
        loadPlayerTexture();
        loadSkins();
        loadBars();
        loadScreenImages();
//        loadFonts();
    }


    public static void dispose(){
        manager.dispose();
    }


}
