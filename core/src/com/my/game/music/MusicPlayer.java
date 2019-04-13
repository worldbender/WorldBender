package com.my.game.music;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.my.game.MyAssetManager;

public class MusicPlayer {
    private MusicPlayer(){}
    private static Music music;
    private static Music music2;
    private static Music music3;
    private static Music music4;
    private static Sound hpUpSound;
    private static Sound opponentDieSound;

    public static void initSounds(){
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music1.mp3"));
        music2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/music2.mp3"));
        music3 = Gdx.audio.newMusic(Gdx.files.internal("sounds/music3.mp3"));
        music4 = Gdx.audio.newMusic(Gdx.files.internal("sounds/music4.mp3"));
        hpUpSound = MyAssetManager.manager.get(MyAssetManager.HP_UP);
        opponentDieSound = MyAssetManager.manager.get(MyAssetManager.MEOW);
    }

    public static void playBackgroundMusic(){
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

    public static void playHpUpSound(){
        hpUpSound.stop();
        hpUpSound.play();
    }

    public static void playOpponentDieSound(){
        opponentDieSound.stop();
        opponentDieSound.play();
    }

}

