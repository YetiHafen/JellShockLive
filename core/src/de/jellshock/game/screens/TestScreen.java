package de.jellshock.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import de.jellshock.game.world.TerrainType;
import de.jellshock.game.world.World;
import de.jellshock.game.world.WorldType;
import de.jellshock.game.vehicles.Tank;

public class TestScreen extends AbstractScreen {

    private SpriteBatch batch;
    private World world;
    private Tank tank = new Tank(new Color(Color.CYAN));


    public TestScreen() {
        batch = new SpriteBatch();
        world = new World(width, height, WorldType.MOUNTAIN, TerrainType.MOUNTAIN);
        world.generateWorld();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            tank.moveX(-100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            tank.moveX(100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            tank.setRotation(tank.getRotation() + 100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F)) {
            tank.setRotation(tank.getRotation() - 100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            tank.setGunRotation(tank.getGunRotation() + 100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            tank.setGunRotation(tank.getGunRotation() - 100 * delta);
        }
        batch.begin();
        world.renderWorld(batch);
        tank.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        tank.dispose();
    }
}
