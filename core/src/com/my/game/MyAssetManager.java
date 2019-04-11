package com.my.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.my.game.bullets.FireRing;
import com.my.game.pickups.InvisibleWarp;
import com.my.game.pickups.Warp;
import com.my.game.player.Ground;
import com.my.game.player.Player;
import com.my.game.player.Water;

public class MyAssetManager {
    private MyAssetManager(){}

    public static final AssetManager manager = new AssetManager();
    public static final int PLAYER_TEXTURE_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_WIDTH"));
    public static final int PLAYER_TEXTURE_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_TEXTURE_HEIGHT"));
    public static final int PLAYER_HEAD_WIDTH = Integer.parseInt(Properties.loadConfigFile("PLAYER_HEAD_WIDTH"));
    public static final int PLAYER_HEAD_HEIGHT = Integer.parseInt(Properties.loadConfigFile("PLAYER_HEAD_HEIGHT"));
    public static final int NUMBER_OF_PLAYER_ANIMATION_FRAMES = 5;

    //bars
    public static final String HEALTH_BAR = "bars/healthBar.png";

    //bullets
    public static final String GRENADE = "bullets/granat.png";
    public static final String SPECTRAL_TEAR = "bullets/spectralTear.png";
    public static final String FIRE_RING_TEXTURE = "bullets/fireRing.png";

    //playerTexture
    public static final String DOWN_WALK = "characters/downIsaacHeadless2.png";
    public static final String LEFT_WALK = "characters/leftWalkIsaacHeadless.png";
    public static final String RIGHT_WALK = "characters/rightWalkIsaacHeadless2.png";
    public static final String UP_WALK = "characters/upIsaacHeadless2.png";
    public static final String PROF_HEADS = "characters/prof.png";
    public static final String BLOND_HEADS = "characters/blondi.png";

    //opponents
    public static final String NIETZSCHE = "opponents/nietzsche.png";
    public static final String POE = "opponents/poe.png";
    public static final String SCHOPEN = "opponents/schopen.png";

    //pickups
    public static final String HP = "pickups/hp.png";
    public static final String INNER_EYE = "pickups/InnerEye.png";
    public static final String SAD_ONION = "pickups/SadOnion.png";
    public static final String WARP = "pickups/warp.png";
    public static final String WARP_ANIMATION = "pickups/warpAnimated.png";
    public static final String OPEN_DOOR_ANIMATION = "pickups/openDoors.png";

    //screenImages
    public static final String TREE = "screenImages/tree.jpg";

    //sounds
    public static final String HP_UP = "sounds/hpup.wav";
    public static final String MEOW = "sounds/meow.mp3";

    //fonts
    public static final String FONT = "skin/font-export.fnt";
    public static final String FONT_MEDIUM = "skin/font-medium-export.fnt";
    public static final String FONT_SMALL = "skin/font-small-export.fnt";
    public static final String FONT_TITLE ="skin/font-title-export.fnt";

    //skins
    public static final String SGX_UI_ATLAS = "skin/sgx-ui.atlas";
    public static final String SGX_UI_JSON = "skin/sgx-ui.json";
    public static final String SGX_UI_PNG = "skin/sgx-ui.png";


    public static void loadBullets(){
        manager.load(GRENADE, Texture.class);
        manager.load(SPECTRAL_TEAR, Texture.class);
        manager.load(FIRE_RING_TEXTURE, Texture.class);
    }

    public static void loadPlayerTexture(){
        manager.load(DOWN_WALK, Texture.class);
        manager.load(LEFT_WALK, Texture.class);
        manager.load(RIGHT_WALK, Texture.class);
        manager.load(UP_WALK, Texture.class);
        manager.load(PROF_HEADS, Texture.class);
        manager.load(BLOND_HEADS, Texture.class);
    }

    public static void loadOpponents(){
        manager.load(NIETZSCHE, Texture.class);
        manager.load(POE, Texture.class);
        manager.load(SCHOPEN, Texture.class);
    }

    public static void loadPickups(){
        manager.load(HP, Texture.class);
        manager.load(INNER_EYE, Texture.class);
        manager.load(SAD_ONION, Texture.class);
        manager.load(WARP, Texture.class);
        manager.load(WARP_ANIMATION, Texture.class);
        manager.load(OPEN_DOOR_ANIMATION, Texture.class);
    }

    public static void loadMusic(){
        manager.load(HP_UP, Sound.class);
        manager.load(MEOW, Sound.class);
    }


    public static void loadBars(){
        manager.load(HEALTH_BAR, Texture.class);
    }

    public static void loadSkins(){
        manager.load(SGX_UI_JSON, Skin.class);
    }

    public static void loadFonts(){
        manager.load(FONT, BitmapFont.class);
        manager.load(FONT_MEDIUM, BitmapFont.class);
        manager.load(FONT_SMALL, BitmapFont.class);
        manager.load(FONT_TITLE, BitmapFont.class);
    }

    public static void loadAnimations(){
        manager.finishLoading();
        Player.downWalkAnimation = getAnimationFrom1DPicture(manager.get(DOWN_WALK), PLAYER_TEXTURE_WIDTH*2, PLAYER_TEXTURE_HEIGHT*2, NUMBER_OF_PLAYER_ANIMATION_FRAMES);
        Player.upWalkAnimation = getAnimationFrom1DPicture(manager.get(UP_WALK), PLAYER_TEXTURE_WIDTH*2, PLAYER_TEXTURE_HEIGHT*2, NUMBER_OF_PLAYER_ANIMATION_FRAMES);
        Player.rightWalkAnimation = getAnimationFrom1DPicture(manager.get(RIGHT_WALK), PLAYER_TEXTURE_WIDTH*2, PLAYER_TEXTURE_HEIGHT*2, 10);
        Player.leftWalkAnimation = getAnimationFrom1DPicture(manager.get(LEFT_WALK), PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 10);
        FireRing.fireRingAnimation = getAnimationFrom1DPicture(manager.get(FIRE_RING_TEXTURE), 64, 64, 10);
        Ground.heads = getAnimationFrom1DPicture(manager.get(PROF_HEADS), PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 4);
        Water.heads = getAnimationFrom1DPicture(manager.get(BLOND_HEADS), PLAYER_TEXTURE_WIDTH, PLAYER_TEXTURE_HEIGHT, 4);
        Warp.warpAnimation = getAnimationFrom1DPicture(manager.get(WARP_ANIMATION), 64, 64, 9);
        InvisibleWarp.openDoorsAnimation = getAnimationFrom1DPicture(manager.get(OPEN_DOOR_ANIMATION), 64, 64, 4);
        Player.headRegion = new TextureRegion(new Texture("characters/prof.png"), 0, 0, PLAYER_HEAD_WIDTH, PLAYER_HEAD_HEIGHT);
    }

    public static void loadScreenImages(){
        manager.load(TREE, Texture.class);
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
        return new Animation<>(0.1f, walkFrames);
    }
}
