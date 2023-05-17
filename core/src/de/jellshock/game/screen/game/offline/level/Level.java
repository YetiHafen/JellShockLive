package de.jellshock.game.screen.game.offline.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import de.jellshock.game.player.Bot;
import de.jellshock.game.screen.game.offline.OfflineScreen;
import de.jellshock.game.world.World;
import de.jellshock.game.world.level.LevelLoader;

import java.util.UUID;

public abstract class Level extends OfflineScreen {

    protected final Array<Bot> bots;

    protected final World world;

    protected final int botCount;

    public Level(World world, int botCount) {
        super(world);
        this.world = world;
        this.botCount = botCount;
        this.bots = new Array<>(botCount);

        for (int i = 0; i < botCount; i++) {
            Bot bot = new Bot(UUID.randomUUID().toString(), world);
            bot.randomSpawn();
            bots.add(bot);
        }

        bots.forEach(bot -> renderObjects.add(bot.getTank()));
    }

    public static World loadWorldFromLvlPath(String levelName) {
        return new LevelLoader().loadLevel(Gdx.files.internal("level/" + levelName + ".level"));
    }
}
