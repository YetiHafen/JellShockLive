package de.jellshock.game.screen.game.offline.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import de.jellshock.game.event.key.KeyEvent;
import de.jellshock.game.player.Bot;
import de.jellshock.game.player.Entity;
import de.jellshock.game.screen.game.offline.OfflineScreen;
import de.jellshock.game.ui.hud.LevelCount;
import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import de.jellshock.game.weapon.implementation.single.ShotProjectile;
import de.jellshock.game.world.World;
import de.jellshock.game.world.level.LevelLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Level extends OfflineScreen {

    protected final List<Bot> bots;
    protected final World world;
    protected final int botCount;

    private final LevelCount levelCount;

    private AbstractWeapon weapon;

    public Level(World world, int level, int botCount) {
        super(world, botCount);
        this.world = world;
        this.botCount = botCount;
        this.bots = new ArrayList<>(botCount);

        levelCount = new LevelCount(this, level);
        renderObjects.add(levelCount);

        for (int i = 0; i < botCount; i++) {
            Bot bot = new Bot(this, UUID.randomUUID().toString(), world);
            bot.getTank().setGunRotation(100);

            entities.add(bot);

            renderObjects.add(bot.getTank());
            renderObjects.add(bot.getHealthBar());

            bots.add(bot);
        }
    }

    public static World loadWorldFromLvl(int levelName) {
        return LevelLoader.loadLevel(Gdx.files.internal("level/level_" + levelName + ".level"));
    }

    @Override
    public void render(float delta) {
        if (blocked) {
            bots.removeAll(entitiesToRemove);
            Bot bot = bots.get(ThreadLocalRandom.current().nextInt(0, entities.size() -1));
            if (weapon != null) weapon.dispose();

            boolean playerLeft = bot.getTank().getPosition() - player.getTank().getPosition() > 0;
            float rotation = playerLeft ? ThreadLocalRandom.current().nextFloat(100, 150) : ThreadLocalRandom.current().nextFloat(10, 60);
            bot.getTank().setGunRotation(rotation);
            weapon = bot.getTank().shootProjectile(this, bot.randomStrength(), ShotProjectile.class);
            blocked = false;
        }
        if (weapon != null) {
            weapon.update(delta);
        }
        super.render(delta);
    }

    @Override
    public void update(float delta, KeyEvent event) {
        if (weapon != null) {
            weapon.render(batch);
        }
        super.update(delta, event);
    }

    @Override
    public void resize(int width, int height) {
        levelCount.setPosition(height);
        super.resize(width, height);
    }

    public void setBotPosition(int index, int position) {
        bots.get(index).getTank().setPosition(position);
    }
}
