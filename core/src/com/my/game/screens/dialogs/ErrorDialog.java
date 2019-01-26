package com.my.game.screens.dialogs;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ErrorDialog extends Dialog {

    public ErrorDialog(Skin skin, Stage stage, String message){
        super("Error", skin);

        text(message);
        button("OK");

        this.show(stage);
    }
}
