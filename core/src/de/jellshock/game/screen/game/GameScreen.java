package de.jellshock.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.jellshock.game.player.Player;
import de.jellshock.game.screen.AbstractScreen;
import de.jellshock.game.world.Map;
import de.jellshock.game.world.MapType;

import java.util.List;

public abstract class GameScreen extends AbstractScreen {

    private final SpriteBatch batch;
    private final InputMultiplexer multiplexer;

    private final Map world;

    private Player localPlayer;
    private List<Player> enemyPlayer;

    public GameScreen() {
        super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch = new SpriteBatch();

        multiplexer = new InputMultiplexer();
        // add stages

        world = new Map(3000, MapType.MOUNTAIN);
        world.generateMap();
    }

    /**
     *
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIGHT_GRAY);
        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        world.render(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.zoom = world.getMapWidth() / (float) Gdx.graphics.getWidth();
        camera.position.x = world.getMapWidth() / 2F;
        camera.position.y = camera.zoom * Gdx.graphics.getHeight() / 2;
        super.resize(width, height);
    }

    protected void checkInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {

        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
}
