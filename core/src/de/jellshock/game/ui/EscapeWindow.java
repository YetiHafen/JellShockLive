package de.jellshock.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.screen.menu.MenuScreen;
import lombok.Getter;

@Getter
public class EscapeWindow extends HudElement {

    private boolean open;
    private final Skin skin;

    private Pixmap blendScreen;
    private Texture blendScreenTexture;

    private final Texture background;

    private int screenWidth;
    private int screenHeight;

    private final int buttonWidth;
    private final int buttonHeight;

    public EscapeWindow(GameScreen gameScreen) {
        super(gameScreen);
        open = false;

        screenWidth = (int) (Gdx.graphics.getWidth() * gameScreen.getCamera().zoom);
        screenHeight = (int) (Gdx.graphics.getHeight() * gameScreen.getCamera().zoom);

        AssetManager manager = JellShock.getInstance().getAssetManager();
        skin = manager.get(Constants.JELLY_SKIN_PATH);

        background = manager.get(Constants.DIALOG_PATH);

        TextButton resume = new TextButton("Resume", skin);

        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleWindow();
            }
        });

        TextButton quit = new TextButton("Quit", skin);

        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().getScreens().remove(gameScreen.getClass());
                JellShock.getInstance().setScreen(MenuScreen.class);
            }
        });

        table.add(resume).uniform().center().row();
        table.add(quit).uniform().center().padTop(15).row();

        buttonWidth = (int) resume.getWidth();
        buttonHeight = (int) resume.getHeight();


        System.out.printf("width: %d, height: %d \n", screenWidth, screenHeight);
        setTablePos();

        updateSize(screenWidth, screenHeight);
    }

    private void setTablePos() {
        int x = screenWidth / 2 - background.getWidth() / 2;
        int y = screenHeight / 2 - background.getHeight() / 2;
        int width = background.getWidth();
        int height = background.getHeight();
        table.setBounds(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        float camZoom = getGameScreen().getCamera().zoom;
        screenWidth = (int) (Gdx.graphics.getWidth() * camZoom);
        screenHeight = (int) (Gdx.graphics.getHeight() * camZoom);

        int width = background.getWidth();
        int height = background.getHeight();

        int x = screenWidth / 2 - width / 2;
        int y = screenHeight / 2 - height / 2;

        setTablePos();

        super.render(spriteBatch);
    }

    public void toggleWindow() {
        if (open) {
            closeWindow();
        } else {
            openWindow();
        }
    }

    private void openWindow() {
        open = true;
        Gdx.input.setInputProcessor(stage);
    }

    private void closeWindow() {
        open = false;
        Gdx.input.setInputProcessor(null);
    }

    public Texture getBlendScreenTexture() {
        return blendScreenTexture;
    }

    public void updateSize(int width, int height) {
        if (blendScreen != null)
            blendScreen.dispose();
        blendScreen = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        blendScreen.setColor(0, 0, 0, 0.5F);
        blendScreen.fillRectangle(0, 0, width, height);
        blendScreenTexture = new Texture(blendScreen);
    }

    @Override
    public void dispose() {
        background.dispose();
        blendScreen.dispose();
        blendScreenTexture.dispose();
        super.dispose();
    }
}
