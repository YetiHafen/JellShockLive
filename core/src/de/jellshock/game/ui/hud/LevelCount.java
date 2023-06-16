package de.jellshock.game.ui.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.HudElement;

public class LevelCount extends HudElement {

    private final GameScreen gameScreen;
    private final Skin skin;

    private static final int TEXT_HEIGHT = 50;

    private final Label levelLabel;

    public LevelCount(GameScreen gameScreen, int level) {
        super(gameScreen, new Vector2(0, Gdx.graphics.getHeight()));
        this.gameScreen = gameScreen;

        skin = JellShock.getInstance().getAssetManager().get(Constants.JELLY_SKIN_PATH);

        levelLabel = new Label("Level " + level, skin);
        table.add(levelLabel);

        //table.setDebug(true);
    }

    public void setPosition(float height) {
        table.setBounds(0, height + 200, levelLabel.getWidth(), levelLabel.getHeight());
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        skin.dispose();
        super.dispose();
    }
}
