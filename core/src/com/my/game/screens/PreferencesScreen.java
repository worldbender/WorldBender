package com.my.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.my.game.MyAssetManager;
import com.my.game.WBGame;
import com.my.game.connection.TCPConnection;
import com.my.game.music.MusicManager;
import com.my.game.screens.dialogs.ErrorDialog;
import org.json.JSONObject;

public class PreferencesScreen extends AbstractScreen{
    private Label titleLabel;
    private Label nameLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Label fullscreenOnOffLabel;

    public PreferencesScreen(WBGame wbgame){
        super(wbgame);
    }

    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        Skin skin = MyAssetManager.manager.get(MyAssetManager.SGX_UI_JSON);

        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(AppPreferences.getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                AppPreferences.setMusicVolume(volumeMusicSlider.getValue());
                MusicManager.setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(AppPreferences.getSoundVolume());
        soundMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                AppPreferences.setSoundVolume(soundMusicSlider.getValue());
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(AppPreferences.isMusicEnabled());
        musicCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean enabled = musicCheckbox.isChecked();
                AppPreferences.setMusicEnabled(enabled);
                MusicManager.playMenuMusic();
            }
        });

        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(AppPreferences.isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                AppPreferences.setSoundEffectsEnabled(enabled);
            }
        });

        final CheckBox fullscreenCheckbox = new CheckBox(null, skin);
        fullscreenCheckbox.setChecked(AppPreferences.isFullScreenEnabled());
        fullscreenCheckbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean enabled = fullscreenCheckbox.isChecked();
                AppPreferences.setFullscreenEnabled(enabled);
                Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
                if (enabled)
                    Gdx.graphics.setFullscreenMode(currentMode);
                else
                    Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
            }
        });

        final TextField nameField = new TextField(null, skin);
        nameField.setText(AppPreferences.getName());
        nameField.setMaxLength(15);

        final TextButton backButton = new TextButton("Back", skin, "small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.changeScreen(WBGame.MENU);

            }
        });

        final TextButton saveButton = new TextButton("Save Name", skin, "small");
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if(nameField.getText().isEmpty()){
                    new ErrorDialog(skin, stage, "Name can not be empty!");
                    nameField.setText(AppPreferences.getName());
                }
                else{
                    AppPreferences.setName(nameField.getText().trim());
                    WBGame.getConnection().getTcp().sendMessage(new JSONObject()
                            .put("msg", "saveName")
                            .put("content", new JSONObject()
                                    .put("name", nameField.getText().trim())));
                }
            }
        });

        titleLabel = new Label( "Preferences", skin );
        nameLabel = new Label( "Name", skin );
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin );
        musicOnOffLabel = new Label( "Music ON/OFF", skin );
        soundOnOffLabel = new Label( "Sound ON/OFF", skin );
        fullscreenOnOffLabel = new Label("Fullscreen ON/OFF", skin);

        table.add(titleLabel).colspan(2);
        table.row().pad(20,0,0,10);

        table.add(nameLabel).left();
        table.add(nameField);
        table.row().pad(10,0,0,10);

        table.add(fullscreenOnOffLabel).left();
        table.add(fullscreenCheckbox);
        table.row().pad(10,0,0,10);

        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);

        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);

        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(10,0,0,10);

        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row().pad(10,0,0,10);

        table.add(backButton);
        table.add(saveButton);
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
        stage.dispose();
    }
}
