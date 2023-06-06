package de.jellshock.game.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.jellshock.game.event.key.KeyEventManager;
import de.jellshock.game.event.key.KeyInputProcessor;
import de.jellshock.game.player.Player;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.AbstractScreen;
import de.jellshock.game.ui.MenuBar;
import de.jellshock.game.weapon.implementation.single.ShotProjectile;
import de.jellshock.game.world.World;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class GameScreen extends AbstractScreen {

    protected final SpriteBatch batch;
    protected final KeyEventManager keyEventManager;
    protected final KeyInputProcessor inputProcessor;

    protected final World world;
    protected final Player player;

    protected MenuBar menuBar;

    protected final List<IRenderConsumer<SpriteBatch>> renderObjects;

    protected ShotProjectile shotProjectile;

    public GameScreen(World world, String playerName) {
        super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch = new SpriteBatch();

        this.world = world;
        player = new Player(playerName, world);

        menuBar = new MenuBar(this);

        renderObjects = new ArrayList<>();
        registerRenderObjects();

        keyEventManager = new KeyEventManager();
        inputProcessor = new KeyInputProcessor(keyEventManager);

        keyEventManager.registerKeyListener(player);
    }

    private void registerRenderObjects() {
        renderObjects.add(world);
        renderObjects.add(player.getTank());
        renderObjects.add(menuBar);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        inputProcessor.keyPressed();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (shotProjectile != null) {
                shotProjectile.dispose();
            }
            shotProjectile = (ShotProjectile) player.getTank().shootProjectile(5, ShotProjectile.class);
        }
        if (shotProjectile != null) {
            shotProjectile.update(delta);
        }
        ScreenUtils.clear(Color.LIGHT_GRAY);
        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        renderObjects.forEach(render -> render.render(batch));
        update(delta);
        batch.end();
    }

    public abstract void update(float delta);

    @Override
    public void resize(int width, int height) {
        int mapWidth = world.getMap().getMapWidth();
        camera.zoom = mapWidth / (float) Gdx.graphics.getWidth();
        camera.position.x = mapWidth / 2F;
        camera.position.y = camera.zoom * Gdx.graphics.getHeight() / 2;
        camera.position.y -= 200;
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        player.dispose();
        if(shotProjectile != null)
            shotProjectile.dispose();
    }
}
