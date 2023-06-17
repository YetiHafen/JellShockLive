package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.screen.AbstractScreen;
import lombok.Getter;

@Getter
public abstract class AbstractMenuScreen extends AbstractScreen {

    protected Texture backgroundTexture;
    protected Texture logoTexture;
    private Texture backButtonTexture;
    private ImageButton backButton;
    protected SpriteBatch spriteBatch;

    protected Stage stage;

    private final boolean renderLogo;
    private final boolean renderBackButton;

    public AbstractMenuScreen() {
        this(true, false);
    }

    public AbstractMenuScreen(boolean renderLogo) {
        this(renderLogo, false);
    }

    public AbstractMenuScreen(boolean renderLogo, boolean renderBackButton) {
        this.renderLogo = renderLogo;
        this.renderBackButton = renderBackButton;
        spriteBatch = new SpriteBatch();
        stage = new Stage();
        AssetManager manager = JellShock.getInstance().getAssetManager();
        backgroundTexture = manager.get(Constants.MENU_BACKGROUND_PATH);
        if (renderBackButton) {
            initBackButton();
        }
        if (renderLogo) {
            logoTexture = manager.get(Constants.LOGO_PATH);
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

    @Override
    public void show() {
        if (backButton != null)
            backButton.setVisible(true);
        super.show();
    }

    private void initBackButton() {
        backButtonTexture = JellShock.getInstance().getAssetManager().get(Constants.LEFT_ARROW_PATH);
        backButton = new ImageButton(new TextureRegionDrawable(backButtonTexture));
        backButton.setSize(30, 30);
        backButton.setPosition(10, Gdx.graphics.getHeight() - 50);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backButton.setVisible(false);
                setSlideScreen(MenuScreen.class, Direction.LEFT);
            }
        });
        stage.addActor(backButton);
    }

    public void setFadeScreen(Class<? extends AbstractScreen> screen) {
        stage.addAction(Actions.sequence(Actions.fadeOut(0.3f), Actions.run(() -> JellShock.getInstance().setScreen(screen))));
    }

    public void setSlideScreen(Class<? extends AbstractScreen> screen, Direction direction)  {
        int directionX = 0;
        int directionY = 0;
        switch (direction) {
            case LEFT -> directionX = Gdx.graphics.getWidth();
            case RIGHT -> directionX = -Gdx.graphics.getWidth();
            case TOP -> directionY = Gdx.graphics.getHeight();
            case BOTTOM -> directionY = -Gdx.graphics.getHeight();
        }
        int finalDirectionY = directionY;
        int finalDirectionX = directionX;
        stage.addAction(
                Actions.sequence(
                        Actions.moveBy(directionX, directionY, 0.4f),
                        Actions.run(() -> {
                            JellShock.getInstance().setScreen(screen);
                            // restore stage positioning
                            stage.addAction(Actions.moveBy(-finalDirectionX, -finalDirectionY, 0));
                        })
                )
        );
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
        if (logoTexture != null)
            logoTexture.dispose();
        if (backButtonTexture != null)
            backButtonTexture.dispose();
        stage.dispose();
        spriteBatch.dispose();
    }

    public enum Direction {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}
