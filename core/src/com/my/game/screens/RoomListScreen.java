package com.my.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.my.game.WBGame;
import com.my.game.rooms.Room;
import com.my.game.screens.dialogs.ErrorDialog;
import com.my.game.screens.dialogs.JoinRoomDialog;
import org.json.JSONObject;

import java.util.List;

public class RoomListScreen extends AbstractScreen {
    private List<Room> rooms;
    private ScrollPane scrollPane;
    private float gameWidth, gameHeight;
    private int boundRatio = 400;
    private String character;
    private int option;

    public RoomListScreen(WBGame game, List<Room> rooms, String character, int option) {
        super(game);
        this.rooms = rooms;
        this.character = character;
        this.option = option;
    }

    @Override
    public void show() {
        initTable();

        TextButton joinRoomById = new TextButton("Join Room by ID", skin);
        TextButton refresh = new TextButton("Refresh List", skin);
        TextButton back = new TextButton("Back to Menu", skin);

        table.add(joinRoomById).fillX().uniformX().bottom();
        table.add(refresh).fillX().uniformX().bottom();
        table.add(back).fillX().uniformX().bottom();
        table.row().pad(30, 0, 30, 0);
        table.add(new Label("", skin)).bottom();
        table.row().pad(50, 0, 50, 0);

        joinRoomById.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new JoinRoomDialog(skin, stage, character);
            }
        });

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(WBGame.MENU);
            }
        });

        refresh.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                WBGame.getConnection().getTcp().sendMessage(new JSONObject()
                        .put("msg", "getRoomList")
                        .put("content", new JSONObject().put("character", character)));
            }
        });

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();

        Table roomList = new Table();
        roomList.align(Align.top);

        initRoomListTable(roomList);

        scrollPane = new ScrollPane(roomList, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setBounds(boundRatio, boundRatio/2, gameWidth - 2*boundRatio, gameHeight- boundRatio);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setTransform(true);

        stage.addActor(scrollPane);

        switch(option){
            case 1:
                new ErrorDialog(skin, stage, "Room owner left!");
                break;
            case 2:
                new ErrorDialog(skin, stage, "Game already started or room is full!");
                break;
            case 3:
                new ErrorDialog(skin, stage, "Room with this ID doesn't exist!");
                break;
            default:
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

    private void initRoomListTable(Table roomList){
        roomList.add(new Label("Room ID", skin, "white")).left().expandX().fillX();
        roomList.add(new Label("Room owner", skin, "white")).center().expandX().fillX();
        roomList.add(new Label("Players in room", skin, "white")).center().expandX().fillX();
        roomList.add(new Label("Game status", skin, "white")).right().expandX().fillX();
        roomList.add(new Label("", skin, "white")).expandX().fill();
        roomList.row();

        for(Room room : rooms){
            final int id = Integer.parseInt(room.getRoomId());
            TextButton joinButton = new TextButton("Join", skin, "default");

            joinButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    WBGame.getConnection().getTcp().sendMessage(new JSONObject()
                            .put("msg", "joinRoom")
                            .put("content", new JSONObject().put("id", id))
                            .put("character", character)
                    );
                }
            });
            roomList.add(new Label(room.getRoomId(), skin, "medium")).left().expandX().fillX();
            roomList.add(new Label(room.getRoomOwner(), skin, "medium")).center().expandX().fillX();
            roomList.add(new Label(room.getPlayersInRoom(), skin, "medium")).center().expandX().fillX();
            if(!room.canPlayerJoin()) {
                joinButton.setDisabled(true);
                joinButton.setTouchable(Touchable.disabled);
                roomList.add(new Label(room.getGameStatus(), skin, "medium-red")).right().expandX().fillX();
            } else roomList.add(new Label(room.getGameStatus(), skin, "medium-green")).right().expandX().fillX();
            roomList.add(joinButton).top().expandX().fill();
            roomList.row();
        }
    }
}
