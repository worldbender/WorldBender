package com.my.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import com.my.game.WBGame;

public abstract class AbstractScreen implements Screen {
    protected WBGame game;

    protected Skin skin = new Skin(Gdx.files.internal("skin/sgx-ui.json"));
    protected Stage stage;
    protected OrthographicCamera camera;
    protected SpriteBatch spriteBatch;
    protected int screenShiftX = 500;
    protected int screenShiftY = 500;
    protected int mapPositionX = 0;
    protected int mapPositionY = 0;

    protected Texture splashImg;

    public AbstractScreen(WBGame game){
        this.game = game;
        createCamera();
        stage = new Stage(new StretchViewport(WBGame.WIDTH, WBGame.HEIGHT, camera));
        spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        splashImg = new Texture("tree.jpg");
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WBGame.WIDTH, WBGame.HEIGHT);
        camera.update();
    }

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void show() {}

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resume() {
        game.setPaused(false);
    }

    @Override
    public void pause() {
        game.setPaused(true);
    }

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        game.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    protected void drawBackground(){
        spriteBatch.begin();
        spriteBatch.draw(splashImg, 0, 0,WBGame.WIDTH, WBGame.HEIGHT);
        spriteBatch.end();
    }

    protected void showScreenMessage(String message){
        Skin skin = new Skin(Gdx.files.internal("skin/sgx-ui.json"));
        BitmapFont font = skin.getFont("title");
        GlyphLayout glyphLayout = new GlyphLayout(font,message);
        float fontX = WBGame.WIDTH/2F - glyphLayout.width/2F;
        float fontY = WBGame.HEIGHT/2F + glyphLayout.height/2F;

        spriteBatch.begin();
        font.draw(spriteBatch, glyphLayout, fontX, fontY);
        spriteBatch.end();
    }
}
