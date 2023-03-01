package de.jellshock.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.jellshock.game.vehicles.Tank;
import de.jellshock.game.world.World;
import de.jellshock.game.world.WorldType;

public class TestScreen extends AbstractScreen {

    private final SpriteBatch batch;
    private final World world;
    private final Tank tank;

    private BitmapFont font;

    public TestScreen() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        world = new World(5000, height, WorldType.MOUNTAIN);
        tank = new Tank(Color.CYAN, world);
        world.generateWorld();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling? GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            tank.moveX(-100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            tank.moveX(100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            tank.setGunRotation(tank.getGunRotation() + 100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            tank.setGunRotation(tank.getGunRotation() - 100 * delta);
        }
        batch.begin();
        world.render(batch);
        tank.render(batch);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, Gdx.graphics.getHeight() - 20);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        tank.dispose();
    }
}
