package com.my.game.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.my.game.MyAssetManager;
import com.my.game.screens.AppPreferences;

public class MusicManager {
    private MusicManager(){}
    private static Music music;
    private static Music music2;
    private static Music music3;
    private static Music music4;
    private static Music menuMusic;
    private static Sound hpUpSound;
    private static Sound opponentDieSound;

    public static void initSounds(){
        music = MyAssetManager.manager.get(MyAssetManager.MUSIC);
        music2 = MyAssetManager.manager.get(MyAssetManager.MUSIC2);
        music3 = MyAssetManager.manager.get(MyAssetManager.MUSIC3);
        music4 = MyAssetManager.manager.get(MyAssetManager.MUSIC4);
        menuMusic = MyAssetManager.manager.get(MyAssetManager.MENU_MUSIC);
        hpUpSound = MyAssetManager.manager.get(MyAssetManager.HP_UP);
        opponentDieSound = MyAssetManager.manager.get(MyAssetManager.DEATH);
    }

    public static void playMenuMusic(){
        if(AppPreferences.isMusicEnabled()) {
            menuMusic.stop();
            menuMusic.play();
            menuMusic.setLooping(true);
        }
        else{
            menuMusic.stop();
        }
    }

    public static void playBackgroundMusic(){
        menuMusic.stop();
        if(AppPreferences.isMusicEnabled()) {
            music.stop();
            music.play();

            music.setOnCompletionListener(music -> {
                music.stop();
                music2.stop();
                music2.play();
            });
            music2.setOnCompletionListener(music -> {
                music2.stop();
                music3.stop();
                music3.play();
            });
            music3.setOnCompletionListener(music -> {
                music3.stop();
                music4.stop();
                music4.play();
            });
            music4.setOnCompletionListener(music -> {
                music4.stop();
                music.stop();
                music.play();
            });
        }
    }

    public static void playHpUpSound(){
        if(AppPreferences.isSoundEffectsEnabled()){
            hpUpSound.stop();
            hpUpSound.play(AppPreferences.getSoundVolume());
        }
    }

    public static void playOpponentDieSound(){
        if(AppPreferences.isSoundEffectsEnabled()) {
            opponentDieSound.stop();
            opponentDieSound.play(AppPreferences.getSoundVolume());
        }
    }

    public static void setMusicVolume(float value) {
        menuMusic.setVolume(value);
        music.setVolume(value);
        music2.setVolume(value);
        music3.setVolume(value);
        music4.setVolume(value);
    }
}

