package de.jellshock.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.jellshock.game.vehicles.Tank;
import de.jellshock.game.vehicles.projectiles.Projectile;
import de.jellshock.game.vehicles.projectiles.TestProjectile;
import de.jellshock.game.world.World;
import de.jellshock.game.world.WorldType;

public class TestScreen extends AbstractScreen {

    private final SpriteBatch batch;
    private final World world;
    private final Tank tank;
    private Projectile projectile;

    private BitmapFont font;

    public TestScreen() {
        super(new ExtendViewport(width, height));
        batch = new SpriteBatch();
        font = new BitmapFont();
        world = new World(2048, WorldType.MOUNTAIN);
        tank = new Tank(Color.CYAN, world);
        world.generateWorld();
        camera.translate(tank.getPosition(), world.getMapHeight((int) tank.getPosition()));
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            tank.moveX(-100 * delta);
            camera.translate(-delta * 100, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            tank.moveX(100 * delta);
            camera.translate(delta * 100, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            tank.setGunRotation(tank.getGunRotation() + 100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            tank.setGunRotation(tank.getGunRotation() - 100 * delta);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if(projectile != null)
                projectile.dispose();
            projectile = tank.shootProjectile(5, TestProjectile.class);
        }
        if(projectile != null)
            projectile.update(delta);
        ScreenUtils.clear(Color.LIGHT_GRAY);
        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        world.render(batch);
        tank.render(batch);
        if(projectile != null)
            projectile.render(batch);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, Gdx.graphics.getHeight() - 20);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        tank.dispose();
        font.dispose();
        projectile.dispose();
    }
}
