package de.jellshock.game.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.screen.game.GameScreen;
import lombok.Getter;

@Getter
public class HudElement implements Disposable {

    private final GameScreen gameScreen;
    private final Vector2 position;

    private final Table table;
    private final Stage stage;

    public HudElement(GameScreen gameScreen, Vector2 position) {
        this.gameScreen = gameScreen;
        this.position = position;
        stage = new Stage(gameScreen.getViewport());
        table = new Table();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}