package com.my.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.my.game.WBGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.my.game.screens.dialogs.ErrorDialog;
import com.my.game.screens.dialogs.JoinRoomDialog;
import org.json.JSONObject;

public class MenuScreen extends AbstractScreen{
    private boolean inGame = false;

    public MenuScreen(WBGame game) {
        super(game);
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
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        //create buttons
        TextButton newRoom = new TextButton("Create Room", skin);
        TextButton roomList = new TextButton("Room List", skin);
        TextButton preferences = new TextButton("Preferences", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton resume = new TextButton("Resume", skin);
        SelectBox selectCharacter = new SelectBox(skin);
        selectCharacter.setItems("Ground", "Water");

        //add buttons to table
        if(inGame){
            table.add(resume).fillX().uniformX();
            table.row().pad(10, 0, 0, 0);
        }
        else {
            table.add(newRoom).fillX().uniformX();
            table.row().pad(10, 0, 0, 0);
            table.add(roomList).fillX().uniformX();
            table.row().pad(10, 0, 0, 0);
            table.add(selectCharacter).fillX().uniformX();
            table.row().pad(10, 0, 0, 0);
        }
        table.add(preferences).fillX().uniformX();
        table.row().pad(10, 0, 0, 0);
        table.add(exit).fillX().uniformX();
        table.row();

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

        roomList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(WBGame.ROOM_LIST, selectCharacter.getSelected().toString(), 0);
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
