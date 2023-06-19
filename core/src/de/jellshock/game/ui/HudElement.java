package de.jellshock.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import lombok.Getter;

@Getter
public class HudElement implements IRenderConsumer<SpriteBatch>, Disposable {

    private final GameScreen gameScreen;

    protected final Table table;
    protected final Stage stage;

    public HudElement(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        stage = new Stage(gameScreen.getViewport());
        table = new Table();
        stage.addActor(table);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
