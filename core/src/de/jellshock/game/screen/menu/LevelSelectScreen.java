package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelSelectScreen extends AbstractMenuScreen {

    public LevelSelectScreen() {
        super(false);

        backButtonTexture = new Texture("menu/left-arrow.png");
        ImageButton button = new ImageButton(new TextureRegionDrawable(backButtonTexture));
        button.setSize(30, 30);
        button.setPosition(10, Gdx.graphics.getHeight() - 50);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setVisible(false);
                setSlideScreen(MenuScreen.class, Direction.LEFT);
            }
        });
        stage.addActor(button);

        Table table = new Table();


        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
