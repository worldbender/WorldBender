package com.my.game.music;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.my.game.MyAssetManager;

public class MusicPlayer {
    private static Sound music;
    private static Sound hpUpSound;
    private static Sound opponentDieSound;

    public MusicPlayer(){

    }

    public static void initSounds(){
        hpUpSound = MyAssetManager.manager.get(MyAssetManager.hpup);
        opponentDieSound = MyAssetManager.manager.get(MyAssetManager.meow);
        music = MyAssetManager.manager.get(MyAssetManager.sound1);
    }

    public static void playBackgroundMusic(){
        music.play();
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

