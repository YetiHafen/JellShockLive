package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import de.jellshock.JellShock;
import de.jellshock.game.screen.TestScreen;

public class MenuScreen extends AbstractMenuScreen {


    private final TextButton.TextButtonStyle textButtonStyle;

    private final TextButton playButton;
    private final TextButton settingsButton;

    private final Stage stage;
    private final Table table;

    public MenuScreen() {
        stage = new Stage();
        table = new Table();

        Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
        textButtonStyle = skin.get(TextButton.TextButtonStyle.class);

        playButton = new TextButton("Play!", textButtonStyle);
        playButton.setSize(100, 80);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(new TestScreen());
            }
        });

        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);

        settingsButton = new TextButton("Settings", textButtonStyle);
        settingsButton.setSize(100, 80);

        table.add(playButton);
        table.row();
        table.add(settingsButton);

        //table.setDebug(true);
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
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
