package com.my.game.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Player extends APlayer {
    public static double maxHp = 10;
    public Player(Texture texture, boolean currentPlayer){
        super();
        this.texture = texture;
        this.setSize(texture.getWidth(), texture.getHeight());
        this.currentPlayer = currentPlayer;
    }
    public Player(String name, String x, String y){
        super();
        this.name = name;
        this.setPosition(Integer.parseInt(x), Integer.parseInt(y));
    }

    public Player(String name, int x, int y){
        super();
        this.name = name;
        this.setPosition(x, y);
    }

    public Player(){

    }
    public void draw(SpriteBatch batch){
        batch.draw(this.texture, (int)this.getX(), (int)this.getY());
        this.drawHp(batch);
        this.drawName(batch);
    }

    private void drawHp(SpriteBatch batch){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect((int)this.getX() - 10, (int)this.getY(), 10, (float)(30 * (this.getHp()/Player.maxHp)));
        shapeRenderer.end();
        batch.begin();
    }

    private void drawName(SpriteBatch batch){
        BitmapFont font = new BitmapFont();
        font.draw(batch, this.getName(), (int)this.getX(), (int)this.getY() + this.texture.getHeight() + font.getCapHeight());
    }

    public boolean isCurrentPlayer(){
        return currentPlayer;
    }

    public String getName(){
        return name;
    }

}
