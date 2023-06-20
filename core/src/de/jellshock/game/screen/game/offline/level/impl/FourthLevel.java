package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.player.Bot;
import de.jellshock.game.screen.game.offline.level.Level;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.World;

public class FourthLevel extends Level {

    public static final int level = 4;

    public FourthLevel() {
        super(new World("World", MapType.MOUNTAIN), 1, 4);
        world.generateWorld();

        bots.forEach(Bot::randomSpawn);
    }
}
