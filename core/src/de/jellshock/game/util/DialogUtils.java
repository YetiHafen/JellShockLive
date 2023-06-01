package de.jellshock.game.util;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogUtils {

    public static void error(String message, Stage stage, Skin skin) {
        Dialog dialog = new Dialog("Error", skin);
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setMovable(false);

        dialog.getContentTable().add(new Label(message, skin));

        dialog.button("OK", true).key(Input.Keys.ENTER, true);
        dialog.show(stage);
    }
}
