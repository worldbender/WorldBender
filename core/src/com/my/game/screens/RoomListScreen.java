package com.my.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.my.game.WBGame;
import com.my.game.rooms.Room;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RoomListScreen extends AbstractScreen {
    private ScrollPane scrollPane;
    private float gameWidth, gameHeight;
    private int bound = 400;

    public RoomListScreen(WBGame game) {
        super(game);
    }

    @Override
    public void show() {
        initTable();

        TextButton refresh = new TextButton("Refresh List", skin);
        TextButton back = new TextButton("Back to Menu", skin);

        table.add(refresh).fillX().uniformX().bottom();
        table.add(back).fillX().uniformX().bottom();
        table.row().pad(50, 0, 50, 0);

        // create button listeners
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(WBGame.MENU);
            }
        });

        refresh.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("REFRESHED!");
            }
        });

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();

        Table listTable = new Table();

        Room[] rooms = new Room[100];

        listTable.add(new Label("Room ID", skin, "white")).left().expandX().fillX();
        listTable.add(new Label("Room owner", skin, "white")).center().expandX().fillX();
        listTable.add(new Label("Players in room", skin, "white")).center().expandX().fillX();
        listTable.add(new Label("Game status", skin, "white")).right().expandX().fillX();
        listTable.add(new Label("", skin, "white")).expandX().fill();
        listTable.row();

        for (int i = 0, k = 0; i < 100; i++) {
            final int id = i;
            rooms[k++] = new Room(i, "Ziutek" + 2*i, i%5);
            final TextButton joinButton = new TextButton("Join", skin, "default");
            joinButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    WBGame.getConnection().getTcp().sendMessage(new JSONObject()
                            .put("msg", "joinRoom")
                            .put("content", new JSONObject().put("id", id))
                            .put("character", "Ground")
                    );
                }
            });
            if(!rooms[i].canPlayerJoin()) {
                joinButton.setDisabled(true);
                joinButton.setTouchable(Touchable.disabled);
            }
            listTable.add(new Label(rooms[i].getRoomId(), skin, "medium")).left().expandX().fillX();
            listTable.add(new Label(rooms[i].getRoomOwner(), skin, "medium")).center().expandX().fillX();
            listTable.add(new Label(rooms[i].getPlayersInRoom(), skin, "medium")).center().expandX().fillX();
            if(rooms[i].canPlayerJoin()) listTable.add(new Label(rooms[i].getGameStatus(), skin, "medium")).right().expandX().fillX();
            else listTable.add(new Label(rooms[i].getGameStatus(), skin, "medium-white")).right().expandX().fillX();
            listTable.add(joinButton).expandX().fill();
            listTable.row();
        }

        scrollPane = new ScrollPane(listTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setBounds(bound, bound/2, gameWidth - 2*bound, gameHeight- bound);
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
        stage.dispose();
    }

}
