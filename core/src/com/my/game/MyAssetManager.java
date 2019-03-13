package com.my.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.my.game.pickups.Warp;
import com.my.game.player.Ground;
import com.my.game.player.Player;
import com.my.game.player.Water;


public class MyAssetManager {

    public static final AssetManager manager = new AssetManager();
    public static final int PLAYER_TEXTURE_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_WIDTH"));
    public static final int PLAYER_TEXTURE_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_HEIGHT"));
    public static final int PLAYER_HEAD_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_HEAD_WIDTH"));
    public static final int PLAYER_HEAD_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_HEAD_HEIGHT"));
    public static final int NUMBER_OF_PLAYER_ANIMATION_FRAMES = 5;

    //bars
    public final static String healthBar = "bars/healthBar.png";

    //bullets
    public final static String granat = "bullets/granat.png";
    public final static String spectralTear = "bullets/spectralTear.png";

    //playerTexture
    public final static String downWalk = "characters/downIsaacHeadless2.png";
    public final static String leftWalk = "characters/leftWalkIsaacHeadless.png";
    public final static String rightWalk = "characters/rightWalkIsaacHeadless2.png";
    public final static String upWalk = "characters/upIsaacHeadless2.png";
    public final static String profHeads = "characters/prof.png";
    public final static String blondiHeads = "characters/blondi.png";

    //opponents
    public final static String nietzsche = "opponents/nietzsche.png";
    public final static String poe = "opponents/poe.png";
    public final static String schopen = "opponents/schopen.png";

    //pickups
    public final static String hp = "pickups/hp.png";
    public final static String InnerEye = "pickups/InnerEye.png";
    public final static String SadOnion = "pickups/SadOnion.png";
    public final static String warp = "pickups/warp.png";
    public final static String warpAnimation = "pickups/warpAnimated.png";

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
        manager.load(downWalk, Texture.class);
        manager.load(leftWalk, Texture.class);
        manager.load(rightWalk, Texture.class);
        manager.load(upWalk, Texture.class);
        manager.load(profHeads, Texture.class);
        manager.load(blondiHeads, Texture.class);
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
        manager.load(warpAnimation, Texture.class);
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

    public static void loadAnimations(){
        manager.finishLoading();
        Player.downWalkAnimation = getAnimationFrom1DPicture(manager.get(downWalk), PLAYER_TEXTURE_WIDTH*2, PLAYER_TEXTURE_HEIGHT*2, NUMBER_OF_PLAYER_ANIMATION_FRAMES);
        Player.upWalkAnimation = getAnimationFrom1DPicture(manager.get(upWalk), PLAYER_TEXTURE_WIDTH*2, PLAYER_TEXTURE_HEIGHT*2, NUMBER_OF_PLAYER_ANIMATION_FRAMES);
        Player.rightWalkAnimation = getAnimationFrom1DPicture(manager.get(rightWalk), PLAYER_TEXTURE_WIDTH*2, PLAYER_TEXTURE_HEIGHT*2, 10);
        Player.leftWalkAnimation = getAnimationFrom1DPicture(manager.get(leftWalk), PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 10);
        Ground.heads = getAnimationFrom1DPicture(manager.get(profHeads), PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 4);
        Water.heads = getAnimationFrom1DPicture(manager.get(blondiHeads), PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 4);
        Warp.warpAnimation = getAnimationFrom1DPicture(manager.get(warpAnimation), 64, 64, 9);
        Texture headsRegion = new Texture("characters/prof.png");
        Player.headRegion = new TextureRegion(headsRegion, 0, 0, PLAYER_HEAD_WIDTH, PLAYER_HEAD_HEIGHT);
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
        loadAnimations();
//        loadFonts();
    }


    public static void dispose(){
        manager.dispose();
    }

    public static Animation<TextureRegion> getAnimationFrom1DPicture(Texture texture, int textureWidth, int textureHeight, int numberOfAnimationFrames){
        TextureRegion[][] arrayOfWalks = TextureRegion.split(texture, textureWidth, textureHeight);
        TextureRegion[] walkFrames = new TextureRegion[numberOfAnimationFrames];
        for (int i = 0; i < numberOfAnimationFrames; i++) {
            walkFrames[i] = arrayOfWalks[0][i];
        }
        return new Animation<TextureRegion>(0.1f, walkFrames);
    }
}
