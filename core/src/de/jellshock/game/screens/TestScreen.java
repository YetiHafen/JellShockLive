package de.jellshock.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import de.jellshock.game.world.TerrainType;
import de.jellshock.game.world.World;
import de.jellshock.game.world.WorldType;

public class TestScreen extends AbstractScreen {

    SpriteBatch batch;
    World world;

    public TestScreen() {
        batch = new SpriteBatch();
        world = new World(width, height, WorldType.MOUNTAIN, TerrainType.MOUNTAIN);
        world.generateWorld();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        world.renderWorld(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
}
