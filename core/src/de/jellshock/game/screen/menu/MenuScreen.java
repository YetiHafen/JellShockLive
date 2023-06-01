package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.screen.game.offline.level.impl.FirstLevel;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.level.LevelLoader;

import java.util.UUID;

public class MenuScreen extends AbstractMenuScreen {

    public MenuScreen() {
        Table table = new Table();

        Skin skin = new Skin(Gdx.files.internal(Constants.NEON_SKIN_PATH));
        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);

        TextButton offlineButton = new TextButton("Play!", textButtonStyle);
        TextButton onlineButton = new TextButton("Online!", textButtonStyle);

        offlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(new FirstLevel());
            }
        });

        onlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(new ServerSelectMenu());
            }
        });

        TextButton testButton = new TextButton("Generate Level", textButtonStyle);
        testButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelLoader.generateLevel(UUID.randomUUID().toString(), 3000, MapType.MOUNTAIN.getAmplitude());
            }
        });

        TextButton uiTest = new TextButton("UI Test Screen", textButtonStyle);
        uiTest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(new ExampleTableMenu());
            }
        });

        TextButton settingsButton = new TextButton("Settings", textButtonStyle);
        settingsButton.setSize(100, 80);

        table.add(offlineButton);
        table.row();
        table.add(onlineButton);
        table.row();
        table.add(settingsButton);
        table.row();
        table.add(testButton);
        table.row();
        table.add(uiTest);
        table.setFillParent(true);

        stage.addActor(table);

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
        stage.dispose();
        super.dispose();
    }
}
