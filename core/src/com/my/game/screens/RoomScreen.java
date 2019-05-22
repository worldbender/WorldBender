package com.my.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.my.game.MyAssetManager;
import com.my.game.WBGame;
import com.my.game.player.PlayerDataWrapper;
import org.json.JSONObject;
import java.util.List;

import static com.my.game.MyAssetManager.*;

public class RoomScreen extends AbstractScreen {
    private boolean isOwner = false;
    private int roomId;
    private String userId;
    private List<PlayerDataWrapper> players;
    private ScrollPane scrollPane;
    private Table playerCards;
    private float gameWidth, gameHeight;
    private int boundRatio = 400;
    private SelectBox selectPlayerCharacter;


    public RoomScreen(WBGame game, int roomId, String userId, List<PlayerDataWrapper> players) {
        super(game);
        this.roomId = roomId;
        this.userId = userId;
        this.players = players;
        playerCards = new Table(skin);
        selectPlayerCharacter = new SelectBox(skin);
        selectPlayerCharacter.setItems("Ground", "Water");
    }

    public RoomScreen(WBGame game, boolean isOwner, int roomId, String userId, List<PlayerDataWrapper> players) {
        this(game, roomId, userId, players);
        this.isOwner = isOwner;
    }

    @Override
    public void show() {
        initTable();

        //create buttons
        TextButton newGame = new TextButton("Start Game", skin);
        TextButton back = new TextButton("Leave Room", skin);
        Label roomLabel = new Label("Room ID: " + roomId, skin);
        roomLabel.setAlignment(Align.center);

        //add buttons to table
        table.add(roomLabel).colspan(2).padBottom(10).fillX().bottom();
        table.row();
        table.add(newGame).padBottom(50).fillX().bottom();
        table.add(back).padBottom(50).fillX().bottom();
        table.row();

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

        initPlayersCards();

        scrollPane = new ScrollPane(playerCards, skin, "no-bg");
        scrollPane.setFadeScrollBars(false);
        scrollPane.setBounds(0, boundRatio/2 - 50, gameWidth, gameHeight - boundRatio);
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

    private void initPlayersCards(){
        TextButton saveCharacter = new TextButton("Save Character", skin);
        saveCharacter.align(Align.center);

        for(PlayerDataWrapper player : players){
            initPlayer(player);
        }

        playerCards.row();
        playerCards.add(saveCharacter).colspan(4).center();
    }

    private void initPlayer(PlayerDataWrapper player){
        Table playerCard = new Table(skin);
        playerCard.setWidth(240);
        playerCard.pad(20, 10, 20, 10);
        playerCard.setBackground("file-menu-bar");

        Label playerName = new Label(player.getName(), skin, "title-white");
        playerName.setFontScale(0.75f);
        playerName.setAlignment(Align.center);

        if(player.isOwner()) {
            Texture texture = MyAssetManager.manager.get(ROOM_OWNER);
            Image crown = new Image(texture);

            Table leaderTable = new Table(skin);
            leaderTable.add(crown).size(40, 40).align(Align.right);
            playerName.setAlignment(Align.left);
            leaderTable.add(playerName).expandX();

            playerCard.add(leaderTable).padBottom(20).expandX().fillX();
        } else playerCard.add(playerName).padBottom(20).expandX().fillX();
        playerCard.row().expandX().fillX();

        Texture texture = null;
        if(player.getCharacter().matches("Ground")) texture = MyAssetManager.manager.get(PROF_ROOM);
        else if(player.getCharacter().matches("Water")) texture = MyAssetManager.manager.get(BLOND_ROOM);
        playerCard.add(new Image(texture)).width(200).height(230).padBottom(20);
        playerCard.row().expandX().fillX();

        Label characterClass = new Label("Character Class:", skin, "default");
        characterClass.setAlignment(Align.center);

        playerCard.add(characterClass).padBottom(10).expandX().fillX();
        playerCard.row().expandX().fillX();

        if(player.getUserId().matches(userId)){
            selectPlayerCharacter.setSelected(player.getCharacter());

            playerCard.add(selectPlayerCharacter).width(180);
            playerCard.row().expandX().fillX();
        } else{
            SelectBox selectCharacter = new SelectBox(skin);
            selectCharacter.setItems("Ground", "Water");
            selectCharacter.setSelected(player.getCharacter());
            selectCharacter.setDisabled(true);

            playerCard.add(selectCharacter).width(180);
            playerCard.row().expandX().fillX();
        }

        playerCards.add(playerCard).pad(10);
    }

    public void refreshPlayerCards(List<PlayerDataWrapper> updatedPlayers){
        players = updatedPlayers;
        playerCards.clear();
        initPlayersCards();
    }
}
