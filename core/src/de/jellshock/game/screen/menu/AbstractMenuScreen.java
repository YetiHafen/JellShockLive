package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import de.jellshock.game.screen.AbstractScreen;

public abstract class AbstractMenuScreen extends AbstractScreen {

    protected Texture backgroundTexture;
    protected Texture logoTexture;
    protected SpriteBatch spriteBatch;

    public AbstractMenuScreen() {
        backgroundTexture = new Texture("menu/background.png");
        logoTexture = new Texture("jellshock.png");
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0 ,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.draw(logoTexture, Gdx.graphics.getWidth() / 2F - logoTexture.getWidth() / 2F, Gdx.graphics.getHeight() - 300);
        spriteBatch.end();
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
        spriteBatch.dispose();
        super.dispose();
    }


}
