package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.jellshock.Constants;
import de.jellshock.JellShock;

public class MenuScreen extends AbstractMenuScreen {

    private final Skin skin;

    public MenuScreen() {
        Table table = new Table();

        skin = new Skin(Gdx.files.internal(Constants.NEON_SKIN_PATH));
        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);

        TextButton offlineButton = new TextButton("Play!", textButtonStyle);
        TextButton onlineButton = new TextButton("Online!", textButtonStyle);

        offlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(LevelSelectScreen.class);
            }
        });

        onlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(CreateAccountMenu.class);
            }
        });

        TextButton settingsButton = new TextButton("Settings", textButtonStyle);
        settingsButton.setSize(100, 80);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(SettingsScreen.class);
            }
        });

/*        TextButton testButton = new TextButton("Generate Level", textButtonStyle);
        testButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelLoader.generateCosLevel("level_4", 3000, MapType.MOUNTAIN.getAmplitude() * 3);
            }
        });*/

        table.add(offlineButton).row();
        table.add(onlineButton).row();
        table.add(settingsButton).row();
        /*table.add(testButton);*/
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
