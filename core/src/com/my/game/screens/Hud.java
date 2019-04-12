package com.my.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.my.game.MyAssetManager;
import com.my.game.WBGame;
import com.my.game.player.Player;

import java.util.HashMap;
import java.util.Map;

public class Hud implements Disposable {
    private Stage stage;
    private SpriteBatch spriteBatch;
    private final int PROGRESS_BAR_HEIGHT = 12;
    private Table table;

    private Map<String, ProgressBar> healthBars = new HashMap<>();
    private LabelStyle labelStyle;
    private Texture healthBarTexture;
    private TextureRegion playerHeadTexture;
    private ProgressBarStyle healthBarStyle;

    public Hud(SpriteBatch spriteBatch, Map<String, Player> players){
        this.spriteBatch = spriteBatch;
        stage = new Stage(new StretchViewport(WBGame.WIDTH, WBGame.HEIGHT, new OrthographicCamera()), this.spriteBatch);

        table = new Table();
        table.left().top();
        table.setFillParent(true);

        createHealthBarsAndIcons(players);

        stage.addActor(table);
    }

    private void createHealthBarsAndIcons(Map<String, Player> players) {
        initHudTextures();
        createPlayerRow(GameplayScreen.currentPlayer);
        for(Player player : players.values()) {
            if(player!=GameplayScreen.currentPlayer)
                createPlayerRow(player);
        }
    }

    public void createPlayerRow(Player player){
        Label playerNameLabel = new Label(player.getName(), labelStyle);
        table.add(playerNameLabel).colspan(2).padTop(10);
        table.row();

        ProgressBar healthBar = new ProgressBar(0f, 100f, 1f, false, healthBarStyle);
        healthBar.setValue(player.getHp());
        healthBar.setAnimateDuration(.5f);

        Stack stack = new Stack();
        stack.setWidth(healthBarTexture.getWidth());
        stack.setHeight(healthBarTexture.getHeight());

        Image characterIconBackground = new Image(new TextureRegionDrawable(new TextureRegion(healthBarTexture, 0, 0, healthBarTexture.getHeight(), healthBarTexture.getHeight())));
        Image playerIcon = new Image(new TextureRegionDrawable(playerHeadTexture));
        playerIcon.setScale(.75f, .75f);
        float originX = (characterIconBackground.getWidth()-(playerIcon.getWidth()*0.75f))-1;
        float originY = (characterIconBackground.getHeight()-(playerIcon.getHeight()*0.75f)/0.5f);
        playerIcon.setOrigin(originX, originY);

        stack.addActor(characterIconBackground);
        stack.addActor(playerIcon);

        table.add(stack).width(healthBarTexture.getHeight()).height(healthBarTexture.getHeight());
        table.add(healthBar).width((float)healthBarTexture.getWidth()- healthBarTexture.getHeight()).height(healthBarTexture.getHeight());

        table.row();
        healthBars.put(player.getName(), healthBar);
    }

    //TODO zmienic jak bedzie AssetManager - poprawione LZ
    private void initHudTextures(){
        Skin skin = MyAssetManager.manager.get(MyAssetManager.SGX_UI_JSON);
        BitmapFont font = skin.getFont("small");
        labelStyle = new LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.BLACK;
        healthBarTexture = MyAssetManager.manager.get(MyAssetManager.HEALTH_BAR);
        playerHeadTexture = new TextureRegion(Player.headRegion);
        healthBarStyle = new ProgressBarStyle();
        healthBarStyle.background = new TextureRegionDrawable(new TextureRegion(healthBarTexture, healthBarTexture.getHeight(), 0, healthBarTexture.getWidth() - healthBarTexture.getHeight() - 5, healthBarTexture.getHeight()));
        healthBarStyle.knob = getUtils(0, 100, Color.BLACK);
        healthBarStyle.knobBefore = getUtils(healthBarTexture.getWidth() - 5, PROGRESS_BAR_HEIGHT, new Color(0xad0810ff));
    }

    public void setHealthBarValue(String name, int health){
        ProgressBar healthBar = healthBars.get(name);
        healthBar.setValue(health);
    }

    private Drawable getUtils(int width, int height, Color color){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        return new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
    }

    public Stage getStage() { return stage; }

    public void dispose(){
        stage.dispose();
    }
}
