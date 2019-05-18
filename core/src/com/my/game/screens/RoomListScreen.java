package com.my.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.my.game.WBGame;

public class RoomListScreen extends AbstractScreen {
    private ScrollPane scrollPane;
    private List<String> list;
    private float gameWidth, gameHeight;
    private int bound = 400;

    public RoomListScreen(WBGame game) {
        super(game);
    }

    @Override
    public void show() {
        initTable();

        TextButton room = new TextButton("Join Room", skin);
        TextButton refresh = new TextButton("Refresh List", skin);
        TextButton back = new TextButton("Leave Room", skin);

        table.add(room).fillX().uniformX().bottom();
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

        room.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println(list.getSelected());
                if(list.getItems().isEmpty()) System.out.println("PUSTO W LUJ");
            }
        });

        gameWidth = Gdx.graphics.getWidth();
        gameHeight = Gdx.graphics.getHeight();
        list = new List<>(skin, "bg");

        String[] strings = new String[100];
        for (int i = 0, k = 0; i < 100; i++) {
            strings[k++] = "String: " + i;
        }

        list.setItems(strings);
        scrollPane = new ScrollPane(list, skin);
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
