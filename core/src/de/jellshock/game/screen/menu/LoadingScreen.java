package de.jellshock.game.screen.menu;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.screen.AbstractScreen;

public class LoadingScreen extends AbstractScreen {

    private final AssetManager manager;

    public LoadingScreen() {
        manager = JellShock.getInstance().getAssetManager();

        loadAssets();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CLEAR);
        if (manager.update()) {
            JellShock.getInstance().setScreen(MenuScreen.class);
        }
    }

    private void loadAssets() {
        AssetManager manager = JellShock.getInstance().getAssetManager();

        // Load skin textures
        manager.load(Constants.JELLY_SKIN_PATH, Skin.class);
        manager.load(Constants.NEON_SKIN_PATH, Skin.class);

        // Load menu assets
        manager.load(Constants.LOGO_PATH, Texture.class);
        manager.load(Constants.MENU_BACKGROUND_PATH, Texture.class);
        manager.load(Constants.PLAY_BUTTON_PATH, Texture.class);
        manager.load(Constants.LEFT_ARROW_PATH, Texture.class);

        // Load game background
        manager.load(Constants.BACKGROUND_PATH, Texture.class);

        // Load gun textures

        manager.finishLoadingAsset(Constants.MENU_BACKGROUND_PATH);
    }

    @Override
    public void dispose() {}

}
