package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class ThirdLevel extends Level {

    private static final String levelName = "level_3";

    public ThirdLevel() {
        super(loadWorldFromLvlPath(levelName), 3, 1);
    }
}
