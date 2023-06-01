package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import de.jellshock.JellShock;
import de.jellshock.game.screen.AbstractScreen;

public abstract class AbstractMenuScreen extends AbstractScreen {

    protected Texture backgroundTexture;
    protected Texture logoTexture;
    protected Texture backButtonTexture;
    protected SpriteBatch spriteBatch;

    protected Stage stage;

    private final boolean renderLogo;
    private final boolean renderBackButton;

    public AbstractMenuScreen() {
        this(true, false);
    }

    public AbstractMenuScreen(boolean renderLogo, boolean renderBackButton) {
        this.renderLogo = renderLogo;
        this.renderBackButton = renderBackButton;
        spriteBatch = new SpriteBatch();
        stage = new Stage();
        backgroundTexture = new Texture("menu/background.png");
        if (renderLogo) {
            logoTexture = new Texture("jellshock.png");
        }
        if (!renderBackButton) return;
        backButtonTexture = new Texture("menu/left-arrow.png");
        ImageButton button = new ImageButton(new TextureRegionDrawable(backButtonTexture));
        button.setSize(30, 30);
        button.setPosition(10, Gdx.graphics.getHeight() - 50);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(new MenuScreen());
            }
        });
        stage.addActor(button);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0 ,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (renderLogo) {
            spriteBatch.draw(logoTexture, Gdx.graphics.getWidth() / 2F - logoTexture.getWidth() / 2F, Gdx.graphics.getHeight() - 300);
        }
        spriteBatch.end();
        stage.act(delta);
        stage.draw();
        update(delta);
    }

    public abstract void update(float delta);

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        logoTexture.dispose();
        backButtonTexture.dispose();
        stage.dispose();
        spriteBatch.dispose();
        super.dispose();
    }


}
