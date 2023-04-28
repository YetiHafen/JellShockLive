package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.jellshock.JellShock;
import de.jellshock.game.screen.game.offline.level.impl.FirstLevel;
import de.jellshock.game.screen.game.online.OnlineScreen;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.level.LevelLoader;

public class MenuScreen extends AbstractMenuScreen {

    private final TextButton.TextButtonStyle textButtonStyle;

    private final TextButton offlineButton;
    private final TextButton onlineButton;
    private final TextButton settingsButton;

    private TextButton testButton;

    private final Stage stage;
    private final Table table;

    public MenuScreen() {
        stage = new Stage();
        table = new Table();

        Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
        textButtonStyle = skin.get(TextButton.TextButtonStyle.class);

        offlineButton = new TextButton("Play!", textButtonStyle);
        onlineButton = new TextButton("Online!", textButtonStyle);

        offlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(new FirstLevel());
            }
        });

        onlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(new OnlineScreen());
            }
        });

        testButton = new TextButton("Generate Level", textButtonStyle);
        testButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new LevelLoader().generateLevel("test", 3000, MapType.MOUNTAIN.getAmplitude());
            }
        });

        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);

        settingsButton = new TextButton("Settings", textButtonStyle);
        settingsButton.setSize(100, 80);

        table.add(offlineButton);
        table.row();
        table.add(onlineButton);
        table.row();
        table.add(settingsButton);
        table.row();
        table.add(testButton);
        table.setFillParent(true);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        super.render(delta);
        spriteBatch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
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
