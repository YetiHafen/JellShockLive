package de.jellshock.game.screen.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.event.key.KeyEvent;
import de.jellshock.game.event.key.KeyEventManager;
import de.jellshock.game.event.key.KeyInputProcessor;
import de.jellshock.game.player.Entity;
import de.jellshock.game.player.Player;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.AbstractScreen;
import de.jellshock.game.ui.EscapeWindow;
import de.jellshock.game.ui.MenuBar;
import de.jellshock.game.ui.hud.StrengthTriangle;
import de.jellshock.game.ui.hud.StrengthWheel;
import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import de.jellshock.game.weapon.implementation.multi.FiveBallProjectile;
import de.jellshock.game.world.World;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class GameScreen extends AbstractScreen {

    protected final SpriteBatch batch;
    protected final KeyEventManager keyEventManager;
    protected final KeyInputProcessor keyInput;

    protected final World world;
    protected final Player player;
    protected final List<Entity> entities;

    protected MenuBar menuBar;
    protected StrengthWheel strengthWheel;
    protected StrengthTriangle strengthTriangle;
    protected EscapeWindow escapeWindow;

    protected final List<IRenderConsumer<SpriteBatch>> renderObjects;

    protected AbstractWeapon shotProjectile;

    public GameScreen(World world) {
        super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch = new SpriteBatch();
        loadAssets();

        this.world = world;
        player = new Player(this, "", world);

        entities = new ArrayList<>();
        entities.add(player);

        menuBar = new MenuBar(this, player);
        strengthWheel = new StrengthWheel(this);
        strengthTriangle = new StrengthTriangle(this, player);
        escapeWindow = new EscapeWindow(this);

        renderObjects = new ArrayList<>();
        registerRenderObjects();

        keyEventManager = new KeyEventManager();
        keyInput = new KeyInputProcessor(keyEventManager);

        keyEventManager.registerKeyListener(player);
    }

    private void registerRenderObjects() {
        renderObjects.add(world);
        renderObjects.add(player.getTank());
        renderObjects.add(strengthWheel);
        renderObjects.add(strengthTriangle);
        renderObjects.add(player.getHealthBar());
        renderObjects.add(menuBar);
    }

    private void loadAssets() {
        AssetManager manager = JellShock.getInstance().getAssetManager();

        // Load tank textures
        manager.load(Constants.CHASSIS_PATH, Texture.class);
        manager.load(Constants.TRACK_PATH, Texture.class);
        manager.load(Constants.GUN_PATH, Texture.class);

        // Load projectiles
        manager.load(Constants.SHOT_PATH, Texture.class);

        manager.finishLoading();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(menuBar.getStage());
    }

    @Override
    public void render(float delta) {
        KeyEvent event = keyInput.keyPressed();
        final GameScreen screen = this;
        menuBar.getFireButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (shotProjectile != null) shotProjectile.dispose();
                shotProjectile = player.getTank().shootProjectile(screen, player.getStrength(), FiveBallProjectile.class);
                if (shotProjectile != null) shotProjectile.update(delta);
            }
        });
        if (event != null) {
            if (event.getType() == KeyEvent.EventType.SHOT) {
                if (shotProjectile != null) shotProjectile.dispose();
                shotProjectile = player.getTank().shootProjectile(this, player.getStrength(), FiveBallProjectile.class);
            }
        }
        if (shotProjectile != null) {
            shotProjectile.update(delta);
        }
        ScreenUtils.clear(Color.LIGHT_GRAY);
        batch.begin();
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if (event != null) {
            switch (event.getType()) {
                case MOVE_LEFT, MOVE_RIGHT -> {
                    if (player.getFuel() == 0) return;
                    Vector2 pos = player.getTank().getParentPosition();
                    strengthWheel.updatePosition(pos);
                    strengthTriangle.updatePosition(pos);
                    player.getHealthBar().updatePosition(pos);
                    player.setFuel(getPlayer().getFuel() - 1);
                    menuBar.updateFuel(player.getFuel());
                }
                case GUN_POWER_UP, GUN_POWER_DOWN -> strengthTriangle.updateStrength(player.getStrength());
                case GUN_ROTATION_LEFT, GUN_ROTATION_RIGHT -> strengthTriangle.updateAngle(Math.toRadians(player.getTank().getGunRotation()));
                case ESCAPE -> escapeWindow.toggleWindow();
            }
        }
        renderObjects.forEach(render -> render.render(batch));
        if (escapeWindow.isOpen()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            batch.draw(escapeWindow.getBlendScreenTexture(), 0, 0);
            escapeWindow.render(batch);

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
        update(delta, event);
        batch.end();
    }

    public abstract void update(float delta, KeyEvent event);

    @Override
    public void resize(int width, int height) {
        int mapWidth = world.getMap().getMapWidth();
        camera.zoom = mapWidth / (float) Gdx.graphics.getWidth();
        camera.position.x = mapWidth / 2F;
        camera.position.y = camera.zoom * Gdx.graphics.getHeight() / 2;
        int cameraOffset = 200;
        camera.position.y -= cameraOffset;
        menuBar.setHeight(cameraOffset, camera.zoom);
        escapeWindow.updateSize((int) (width * camera.zoom), (int) (height * camera.zoom));
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        player.dispose();
        menuBar.dispose();
        strengthWheel.dispose();
        if(shotProjectile != null)
            shotProjectile.dispose();
    }
}
