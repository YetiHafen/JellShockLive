package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

    public AbstractMenuScreen() {
        this(true);
    }

    public AbstractMenuScreen(boolean renderLogo) {
        this.renderLogo = renderLogo;
        spriteBatch = new SpriteBatch();
        stage = new Stage();
        backgroundTexture = new Texture("menu/background.png");
        if (renderLogo) {
            logoTexture = new Texture("jellshock.png");
        }
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
        update(delta);
    }

    public void setFadeScreen(AbstractScreen screen) {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.run(() -> JellShock.getInstance().setScreen(screen))));
    }

    public void setSlideScreen(AbstractScreen screen, Direction direction)  {
        int directionX = 0;
        int directionY = 0;
        switch (direction) {
            case LEFT -> directionX = Gdx.graphics.getWidth();
            case RIGHT -> directionX = -Gdx.graphics.getWidth();
            case TOP -> directionY = Gdx.graphics.getHeight();
            case BOTTOM -> directionY = -Gdx.graphics.getHeight();
        }
        System.out.println(directionX);
        System.out.println(directionY);
        stage.addAction(Actions.sequence(Actions.moveBy(directionX, directionY, 0.3f),
                Actions.run(() -> JellShock.getInstance().setScreen(screen))));
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

    public enum Direction {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}
