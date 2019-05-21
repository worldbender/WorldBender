package com.my.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.my.game.MyAssetManager;
import com.my.game.WBGame;
import com.my.game.player.Player;
import org.json.JSONObject;

import static com.my.game.MyAssetManager.*;

public class RoomScreen extends AbstractScreen {
    private boolean isOwner = false;
    private int roomId;
    private ScrollPane scrollPane;
    private float gameWidth, gameHeight;
    private int boundRatio = 400;

    public RoomScreen(WBGame game, int roomId) {
        super(game);
        this.roomId = roomId;
    }

    public RoomScreen(WBGame game, boolean isOwner, int roomId) {
        this(game, roomId);
        this.isOwner = isOwner;
    }

    @Override
    public void show() {
        initTable();

        //create buttons
        TextButton newGame = new TextButton("Start Game", skin);
        TextButton back = new TextButton("Leave Room", skin);
        Label roomLabel = new Label("Room ID: " + roomId, skin);

        //add buttons to table
        table.add(roomLabel).fillX().uniformX().bottom();
        table.row().pad(50, 0, 50, 0);
        table.add(newGame).fillX().uniformX().bottom();
        table.add(back).fillX().uniformX().bottom();
        table.row().pad(50, 0, 50, 0);

        // create button listeners
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                WBGame.getConnection().getTcp().sendMessage(new JSONObject()
                        .put("msg", "leaveRoom")
                        .put("content", new JSONObject().put("port", WBGame.getConnection().getSocket().getLocalPort())));
                game.changeScreen(WBGame.MENU);
            }
        });

        if(isOwner) {
            newGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    WBGame.getConnection().getTcp().sendMessage(new JSONObject()
                            .put("msg", "startGame")
                            .put("content", new JSONObject().put("port", WBGame.getConnection().getSocket().getLocalPort())));
                }
            });
        }
        else {
            newGame.setDisabled(true);
            newGame.setTouchable(Touchable.disabled);
        }

        gameWidth = WBGame.WIDTH;
        gameHeight = WBGame.HEIGHT;

        Table players = new Table(skin);
        players.align(Align.top);

        initPlayer(players, "player1", PROF_ROOM);
        initPlayer(players, "player2", BLOND_ROOM);
        initPlayer(players, "player3", PROF_ROOM);
        initPlayer(players, "player4", BLOND_ROOM);
        players.row();

        scrollPane = new ScrollPane(players, skin, "no-bg");
        scrollPane.setFadeScrollBars(false);
        scrollPane.setBounds(boundRatio - 100, boundRatio/2, gameWidth - 2*boundRatio + 200, gameHeight - boundRatio);
//        scrollPane.setBounds(boundRatio, boundRatio/2, gameWidth - 2*boundRatio, gameHeight - boundRatio);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setTransform(true);

        stage.addActor(scrollPane);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        drawBackground();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        //ignore
    }

    @Override
    public void resume() {
        //ignore
    }

    @Override
    public void hide() {
        //ignore
    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }

    private void initPlayer(Table players, String player, String type){
        SelectBox selectCharacter = new SelectBox(skin);
        selectCharacter.setItems("Ground", "Water");

        Table subtable = new Table(skin);
        subtable.setBackground("file-menu-bar");

        Label p1 = new Label(player, skin, "white");
        p1.setAlignment(Align.center);
        subtable.add(p1).width(200).padTop(10).padBottom(20).expandX().fillX();
        subtable.row().expandX().fillX();
        Texture texture = MyAssetManager.manager.get(type);
        TextureRegion playerHeadTexture = new TextureRegion(Player.headRegion);
//        subtable.add(new Image(new TextureRegionDrawable(playerHeadTexture))).width(200).height(200);
        subtable.add(new Image(texture)).width(200).height(230).padBottom(20);
        subtable.row().expandX().fillX();
        Label characterClass = new Label("Character Class:", skin, "white");
        characterClass.setAlignment(Align.center);
        subtable.add(characterClass).padBottom(10).expandX().fillX();
        subtable.row().expandX().fillX();
        subtable.add(selectCharacter).width(180).padBottom(20);
        subtable.row().expandX().fillX();

        players.add(subtable).pad(10);
    }
}
