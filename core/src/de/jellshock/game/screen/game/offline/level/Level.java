package de.jellshock.game.screen.game.offline.level;

import com.badlogic.gdx.utils.Array;
import de.jellshock.game.player.Bot;
import de.jellshock.game.screen.game.offline.OfflineScreen;

import java.util.UUID;

public abstract class Level extends OfflineScreen {

    protected final Array<Bot> bots;

    protected final int botIndex;

    public Level(int botIndex) {
        this.botIndex = botIndex;
        this.bots = new Array<>(botIndex);

        for (int i = 0; i < botIndex; i++) {
            Bot bot = new Bot(UUID.randomUUID().toString(), world);
            bot.randomSpawn();
            bots.add(bot);
        }

        bots.forEach(bot -> renderObjects.add(bot.getTank()));
    }
}
