package com.my.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.my.game.MyAssetManager;
import com.my.game.WBGame;
import org.json.JSONObject;

import static com.my.game.MyAssetManager.*;

public class RoomScreen extends AbstractScreen {
    private boolean isOwner = false;
    private int roomId;
    private ScrollPane scrollPane;
    private Table playerCards;
    private float gameWidth, gameHeight;
    private int boundRatio = 400;
    private SelectBox selectPlayerCharacter;


    public RoomScreen(WBGame game, int roomId) {
        super(game);
        this.roomId = roomId;
        playerCards = new Table(skin);
        selectPlayerCharacter = new SelectBox(skin);
        selectPlayerCharacter.setItems("Ground", "Water");
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

        playerCards.align(Align.top);

        initPlayers();

        scrollPane = new ScrollPane(playerCards, skin, "no-bg");
        scrollPane.setBounds(boundRatio - 100, boundRatio/2 - 50, gameWidth - 2*boundRatio + 200, gameHeight - boundRatio);
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

    private void initPlayers(){
        initPlayer("player1", PROF_ROOM);
        initPlayer("player2", BLOND_ROOM);
        initPlayer("player3", PROF_ROOM);
        initPlayer("player4", BLOND_ROOM);
        playerCards.row();
    }

    private void initPlayer(String playerx, String type){
        SelectBox selectCharacter = new SelectBox(skin);
        selectCharacter.setItems("Ground", "Water");
        selectCharacter.setSelected("Water");
        selectCharacter.setDisabled(true);

        Table playerCard = new Table(skin);
        playerCard.setBackground("file-menu-bar");

        Label player = new Label(playerx, skin, "title-white");
        player.setFontScale(0.75f);
        player.setAlignment(Align.center);
        playerCard.add(player).width(200).padTop(10).padBottom(20).expandX().fillX();
        playerCard.row().expandX().fillX();

        Texture texture = MyAssetManager.manager.get(type);
        playerCard.add(new Image(texture)).height(230).padBottom(20);
        playerCard.row().expandX().fillX();

        Label characterClass = new Label("Character Class:", skin, "default");
        characterClass.setAlignment(Align.center);
        playerCard.add(characterClass).padBottom(10).expandX().fillX();
        playerCard.row().expandX().fillX();

        playerCard.add(selectCharacter).width(180).padBottom(20);
        playerCard.row().expandX().fillX();

        playerCards.add(playerCard).pad(10);
    }
}
