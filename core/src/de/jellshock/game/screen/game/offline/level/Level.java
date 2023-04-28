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

    protected final int botIndex;

    public Level(World world, int botIndex) {
        super(world);
        this.world = world;
        this.botIndex = botIndex;
        this.bots = new Array<>(botIndex);

        for (int i = 0; i < botIndex; i++) {
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
