package de.jellshock.game.ui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.HudElement;

public class LevelCount extends HudElement {

    private final Skin skin;

    private final Label levelLabel;

    public LevelCount(GameScreen gameScreen, int level) {
        super(gameScreen);

        skin = JellShock.getInstance().getAssetManager().get(Constants.JELLY_SKIN_PATH);

        levelLabel = new Label("Level " + level, skin);
        table.add(levelLabel);
    }

    public void setPosition(float height) {
        table.setBounds(20, height + 200, levelLabel.getWidth(),
                levelLabel.getHeight());
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        skin.dispose();
    }
}
