package com.my.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.my.game.WBGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.my.game.screens.dialogs.ErrorDialog;
import com.my.game.screens.dialogs.JoinRoomDialog;

public class MenuScreen extends AbstractScreen{
    private boolean isRoomFull = false;
    private boolean roomExists = true;

    public MenuScreen(WBGame game) {
        super(game);
        if(!WBGame.connectionStatus) {
            this.create();
        }
    }

    public MenuScreen(WBGame game, boolean isRoomFull, boolean roomExists) {
        super(game);
        if(!WBGame.connectionStatus) {
            this.create();
        }
        this.isRoomFull = isRoomFull;
        this.roomExists = roomExists;
    }

    public void create() {
        try{
            WBGame.connection.createConnection();
            System.out.println("Nawiązano połączenie z serwerem");
            WBGame.connectionStatus = true;
        }catch(Exception e){
            System.out.println("Nie nawiązano połączenia");
        }
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        //create buttons
        TextButton newRoom = new TextButton("Create Room", skin);
        TextButton joinRoom = new TextButton("Join Room", skin);
        TextButton preferences = new TextButton("Preferences", skin);
        TextButton exit = new TextButton("Exit", skin);

        //add buttons to table
        table.add(newRoom).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(joinRoom).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(preferences).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(exit).fillX().uniformX();
        table.row();

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new ErrorDialog(skin, stage, "Preferences not ready yet!");
            }
        });

        newRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                WBGame.connection.tcp.sendMessage("newRoom:" + WBGame.connection.socket.getLocalPort());
                game.changeScreen(WBGame.ROOM_OWNER);
            }
        });

        joinRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new JoinRoomDialog(skin, stage);
            }
        });

        if(isRoomFull){
            new ErrorDialog(skin, stage, "Game already started or room is full!");
        }

        if(!roomExists){
            new ErrorDialog(skin, stage, "Room with this ID doesn't exist!");
        }

    }

    @Override
    public void render(float delta) {
        if(!WBGame.connectionStatus){
            game.changeScreen(WBGame.SPLASH);
            try{
                WBGame.connection.createConnection();
                WBGame.connectionStatus = true;
                game.changeScreen(WBGame.MENU);
            }catch(Exception e){
                System.out.println("Nie nawiązano połączenia");
            }
        }
        else {
            super.render(delta);

            drawBackground();

            stage.act();
            stage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }
}
