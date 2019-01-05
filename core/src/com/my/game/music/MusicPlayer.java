package com.my.game.music;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class MusicPlayer {
    private Music music;
    private static Music staticMusic;
    //instance version
    public MusicPlayer(){
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/sound1.mp3"));
    }
    public MusicPlayer(String pathToMusic){
        Music music = Gdx.audio.newMusic(Gdx.files.internal(pathToMusic));
    }
    public void playMusic(String pathToMusic){
        music = Gdx.audio.newMusic(Gdx.files.internal(pathToMusic));
    }
    public void playMusic(){
        //music.play();
    }
    public void setVolume(float volume){
        music.setVolume(volume);
    }
    //Static version
    public static void initMusic(){
        staticMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/sound1.mp3"));
    }
    public static void initMusic(String pathToString){
        staticMusic = Gdx.audio.newMusic(Gdx.files.internal(pathToString));
    }
    public static void playStaticMusic(){
        staticMusic.setVolume(0.1f);
        staticMusic.play();
    }
    public static void setStaticVolume(float volume){
        staticMusic.setVolume(volume);
    }
}
