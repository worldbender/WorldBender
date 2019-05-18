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
    private List<Room> rooms;
    private ScrollPane scrollPane;
    private float gameWidth, gameHeight;
    private int bound = 400;

    public RoomListScreen(WBGame game) {
        super(game);
        rooms = new ArrayList<>();

        fillFakeRooms();
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
                game.changeScreen(WBGame.ROOM_LIST);
            }
        });

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();

        Table roomList = new Table();
        initRoomList(roomList);

        scrollPane = new ScrollPane(roomList, skin);
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

    private void initRoomList(Table roomList){
        roomList.add(new Label("Room ID", skin, "white")).left().expandX().fillX();
        roomList.add(new Label("Room owner", skin, "white")).center().expandX().fillX();
        roomList.add(new Label("Players in room", skin, "white")).center().expandX().fillX();
        roomList.add(new Label("Game status", skin, "white")).right().expandX().fillX();
        roomList.add(new Label("", skin, "white")).expandX().fill();
        roomList.row();

        //TODO: character injection
        for(Room room : rooms){
            final int id = Integer.parseInt(room.getRoomId());
            TextButton joinButton = new TextButton("Join", skin, "default");

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
            roomList.add(new Label(room.getRoomId(), skin, "medium")).left().expandX().fillX();
            roomList.add(new Label(room.getRoomOwner(), skin, "medium")).center().expandX().fillX();
            roomList.add(new Label(room.getPlayersInRoom(), skin, "medium")).center().expandX().fillX();
            if(!room.canPlayerJoin()) {
                joinButton.setDisabled(true);
                joinButton.setTouchable(Touchable.disabled);
                roomList.add(new Label(room.getGameStatus(), skin, "medium-red")).right().expandX().fillX();
            } else roomList.add(new Label(room.getGameStatus(), skin, "medium-green")).right().expandX().fillX();
            roomList.add(joinButton).expandX().fill();
            roomList.row();
        }
    }

    //TODO: temporal method
    private void fillFakeRooms(){
        for (int i = 0; i < 100; i++) {
            rooms.add(new Room(i, "Ziutek" + 2*i, i%5));
        }
    }
}
