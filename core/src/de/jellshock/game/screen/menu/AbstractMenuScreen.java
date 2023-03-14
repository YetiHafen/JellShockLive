package de.jellshock.game.screen.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.jellshock.game.screen.AbstractScreen;
import de.jellshock.game.ui.Background;

public abstract class AbstractMenuScreen extends AbstractScreen {

    protected SpriteBatch spriteBatch;
    protected Background background;

    public AbstractMenuScreen() {
        super(new ScreenViewport(camera));
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        super.dispose();
    }
}
