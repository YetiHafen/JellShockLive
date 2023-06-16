package de.jellshock.game.screen.game.offline.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import de.jellshock.game.player.Bot;
import de.jellshock.game.screen.game.offline.OfflineScreen;
import de.jellshock.game.ui.hud.LevelCount;
import de.jellshock.game.world.World;
import de.jellshock.game.world.level.LevelLoader;

import java.util.UUID;

public abstract class Level extends OfflineScreen {

    protected final Array<Bot> bots;
    protected final World world;
    protected final int botCount;

    private final LevelCount levelCount;

    public Level(World world, int level, int botCount) {
        super(world);
        this.world = world;
        this.botCount = botCount;
        this.bots = new Array<>(botCount);

        levelCount = new LevelCount(this, level);
        renderObjects.add(levelCount);

        for (int i = 0; i < botCount; i++) {
            Bot bot = new Bot(UUID.randomUUID().toString(), world);
            bot.randomSpawn();
            bot.getTank().setGunRotation(100);
            bots.add(bot);
        }

        bots.forEach(bot -> renderObjects.add(bot.getTank()));
    }

    public static World loadWorldFromLvlPath(String levelName) {
        return LevelLoader.loadLevel(Gdx.files.internal("level/" + levelName + ".level"));
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
