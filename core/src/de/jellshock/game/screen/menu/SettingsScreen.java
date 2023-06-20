package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.jellshock.Constants;

public class SettingsScreen extends AbstractMenuScreen {

    private final Table table;
    private final Skin skin;

    private boolean fullScreen;

    public SettingsScreen() {
        super(false, true);

        table = new Table();

        skin = new Skin(Gdx.files.internal(Constants.NEON_SKIN_PATH));

        Label fullScreenLabel = new Label("FullScreen: ", skin);

        CheckBox fullScreenBox = new CheckBox("", skin);

        fullScreenBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                fullScreen = !fullScreen;
                if (fullScreen)
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                else
                    Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
        });

        table.add(fullScreenLabel);
        table.add(fullScreenBox).row();
        table.setFillParent(true);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
    }

    @Override
    public void update(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
        super.dispose();
    }
}
