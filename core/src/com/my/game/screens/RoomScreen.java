package com.my.game.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.my.game.WBGame;
import org.json.JSONObject;

public class RoomScreen extends AbstractScreen {
    private boolean isOwner = false;
    private int roomId;

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
        Table table = new Table();
        table = table.bottom();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

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
                WBGame.connection.tcp.sendMessage(new JSONObject()
                        .put("msg", "leaveRoom")
                        .put("content", new JSONObject().put("port", WBGame.connection.socket.getLocalPort())));
                game.changeScreen(WBGame.MENU);
            }
        });

        if(isOwner) {
            newGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    WBGame.connection.tcp.sendMessage(new JSONObject()
                            .put("msg", "startGame")
                            .put("content", new JSONObject().put("port", WBGame.connection.socket.getLocalPort())));
                }
            });
        }
        else {
            newGame.setDisabled(true);
            newGame.setTouchable(Touchable.disabled);
        }
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }
}
