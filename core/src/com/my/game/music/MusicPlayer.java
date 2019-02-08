package com.my.game.music;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class MusicPlayer {
    private static Music music;
    private static Music hpUpSound;
    private static Music opponentDieSound;

    public MusicPlayer(){

    }

    public static void initSounds(){
        hpUpSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/hpup.wav"));
        opponentDieSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/meow.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/sound1.mp3"));
    }

    public static void playBackgroundMusic(){
        music.play();
    }

    public static void playHpUpSound(){
        hpUpSound.play();
    }

    public static void playOpponentDieSound(){
        opponentDieSound.play();
    }

}
