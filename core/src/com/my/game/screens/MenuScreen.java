package com.my.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.my.game.WBGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.my.game.screens.dialogs.ErrorDialog;
import com.my.game.screens.dialogs.JoinRoomDialog;
import org.json.JSONObject;

import static com.my.game.MyAssetManager.GAME_NAME;

public class MenuScreen extends AbstractScreen{
    private boolean isRoomFull = false;
    private boolean roomExists = true;
    private boolean inGame = false;

    public MenuScreen(WBGame game) {
        super(game);
    }

    public MenuScreen(WBGame game, boolean isRoomFull, boolean roomExists) {
        this(game);
        this.isRoomFull = isRoomFull;
        this.roomExists = roomExists;
    }

    public MenuScreen(WBGame game, boolean inGame) {
        this(game);
        this.inGame = inGame;
    }

    public void createConnection() {
        showScreenMessage("Connecting ...");
        try{
            WBGame.getConnection().createConnection();
            System.out.println("Nawiązano połączenie z serwerem");
            WBGame.connectionStatus = true;
        }catch(Exception e){
            System.out.println("Nie nawiązano połączenia");
        }
    }

    @Override
    public void show() {
        Texture texture = new Texture(GAME_NAME);
        Image gameNameImage = new Image(texture);

        Table table = new Table();
        table.top().padTop(100).setFillParent(true);
        table.setDebug(false);

        Table buttonsTable = new Table();
        buttonsTable.setFillParent(true);
        buttonsTable.setDebug(false);

        stage.addActor(table);
        stage.addActor(buttonsTable);

        //create buttons
        TextButton newRoom = new TextButton("Create Room", skin);
        TextButton joinRoom = new TextButton("Join Room", skin);
        TextButton preferences = new TextButton("Preferences", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton resume = new TextButton("Resume", skin);
        SelectBox selectCharacter = new SelectBox(skin);
        selectCharacter.setItems("Ground", "Water");
        //add buttons to table
        if(inGame){
            buttonsTable.add(resume).fillX().uniformX();
            buttonsTable.row().pad(10, 0, 0, 0);
        }
        else {
            table.add(gameNameImage);
            buttonsTable.add(newRoom).fillX().uniformX();
            buttonsTable.row().pad(10, 0, 0, 0);
            buttonsTable.add(joinRoom).fillX().uniformX();
            buttonsTable.row().pad(10, 0, 0, 0);
            buttonsTable.add(selectCharacter).fillX().uniformX();
            buttonsTable.row().pad(10, 0, 0, 0);
        }
        buttonsTable.add(preferences).fillX().uniformX();
        buttonsTable.row().pad(10, 0, 0, 0);
        buttonsTable.add(exit).fillX().uniformX();
        buttonsTable.row();

        // create button listeners
        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new ErrorDialog(skin, stage, "Preferences not ready yet!");
            }
        });

        newRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                WBGame.getConnection().getTcp().sendMessage(new JSONObject()
                        .put("msg", "newRoom")
                        .put("content", new JSONObject().put("port", WBGame.getConnection().getSocket().getLocalPort()))
                        .put("character", selectCharacter.getSelected().toString()));

            }
        });

        joinRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                new JoinRoomDialog(skin, stage, selectCharacter.getSelected().toString());
            }
        });

        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(WBGame.PLAY);
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        if(isRoomFull && !roomExists){
            new ErrorDialog(skin, stage, "Room owner left!");
        }

        if(isRoomFull && roomExists){
            new ErrorDialog(skin, stage, "Game already started or room is full!");
        }

        if(!roomExists && !isRoomFull){
            new ErrorDialog(skin, stage, "Room with this ID doesn't exist!");
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        drawBackground();

        if(!WBGame.connectionStatus){
            this.createConnection();
        }
        else {
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
}
