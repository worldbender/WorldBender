package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.my.game.WBGame;

public class RoomScreen extends AbstractScreen {
    private boolean isOwner = false;
    public RoomScreen(WBGame game) {
        super(game);
    }

    public RoomScreen(WBGame game, boolean isOwner) {
        super(game);
        this.isOwner = isOwner;
    }

    @Override
    public void show() {
        Table table = new Table();
        table = table.bottom();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        // temporary until we have asset manager in
        Skin skin = new Skin(Gdx.files.internal("skin/sgx-ui.json"));

        //create buttons
        TextButton newGame = new TextButton("Start Game", skin);
        TextButton back = new TextButton("Leave Room", skin);

        //add buttons to table
        table.add(newGame).fillX().uniformX().bottom();
        table.add(back).fillX().uniformX().bottom();
        table.row().pad(30, 0, 30, 0);

        // create button listeners
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                WBGame.connection.tcp.sendMessage("leaveRoom:" + WBGame.connection.socket.getLocalPort());
                game.changeScreen(WBGame.MENU);
            }
        });

        //TODO: wysłanie wszystkim pakietu że gra sie zaczeła
        if(isOwner) {
            newGame.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    WBGame.connection.tcp.sendMessage("startGame:" + WBGame.connection.socket.getLocalPort());
//                    game.changeScreen(WBGame.PLAY);
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
