package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.jellshock.game.screen.AbstractScreen;

public abstract class AbstractMenuScreen extends AbstractScreen {

    protected Texture backgroundTexture;
    protected Texture logoTexture;
    protected SpriteBatch spriteBatch;

    public AbstractMenuScreen() {
        backgroundTexture = new Texture("background/background.png");
        logoTexture = new Texture("jellshock.png");
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        spriteBatch.draw(backgroundTexture, 0 ,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.draw(logoTexture, Gdx.graphics.getWidth() / 2F, 100);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        spriteBatch.dispose();
        super.dispose();
    }
}
