package de.jellshock.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.jellshock.game.vehicle.Tank;
import de.jellshock.game.weapon.implementation.single.ShotProjectile;
import de.jellshock.game.world.World;
import de.jellshock.game.world.WorldType;

public class TestScreen extends AbstractScreen {

    private final SpriteBatch batch;
    private final World world;
    private final Tank tank;
    private ShotProjectile shotProjectile;

    private BitmapFont font;

    public TestScreen() {
        super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch = new SpriteBatch();
        font = new BitmapFont();
        world = new World(3000, WorldType.MOUNTAIN);
        tank = new Tank(Color.CYAN, world);
        tank.setPosition(world.getMapWidth() / 2F);
        world.generateWorld();

        camera.position.x = world.getMapWidth() / 2F;
        camera.position.y = world.getMapHeight() / 2F;
    }

    @Override
    public void resize(int width, int height) {
        camera.zoom = world.getMapWidth() / (float) Gdx.graphics.getWidth();
        camera.position.x = world.getMapWidth() / 2F;
        camera.position.y = camera.zoom * Gdx.graphics.getHeight() / 2;
        super.resize(width, height);
    }

    @Override
    public void render(float delta) {
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if(shotProjectile != null)
                shotProjectile.dispose();

            shotProjectile = (ShotProjectile) tank.shootProjectile(5, ShotProjectile.class);
        }
        if(shotProjectile != null)
            shotProjectile.update(delta);
        ScreenUtils.clear(Color.LIGHT_GRAY);
        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        world.render(batch);
        tank.render(batch);
        if(shotProjectile != null)
            shotProjectile.render(batch);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, world.getMapHeight());
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        tank.dispose();
        font.dispose();
        shotProjectile.dispose();
    }

}
