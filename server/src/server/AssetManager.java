package server;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetManager {

    public final AssetManager manager = new AssetManager();

    //Isaaac
    public final String downIsaac 		= "../../core/assets/isaac/downIsaac.png";
    public final String leftWalkIsaac 		= "../../core/assets/isaac/leftWalkIsaac.png";
    public final String rightWalkIsaac 		= "../../core/assets/isaac/rightWalkIsaac.png";
    public final String upIsaac 		= "../../core/assets/isaac/upIsaac.png";

    //opponents
    public final String nietzsche 		= "../../core/assets/opponents/nietzsche.png";
    public final String poe 		= "../../core/assets/opponents/poe.png";
    public final String schopen 		= "../../core/assets/opponents/schopen.png";

    //pickups
    public final String hp 		= "../../core/assets/pickups/hp.png";
    public final String InnerEye 		= "../../core/assets/pickups/InnerEye.png";
    public final String SadOnion 		= "../../core/assets/pickups/SadOnion.png";



    public void loadImages(){
        manager.load(downIsaac, Texture.class);

    }
}
