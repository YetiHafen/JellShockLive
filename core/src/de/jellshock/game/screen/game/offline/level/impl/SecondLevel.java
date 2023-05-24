package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class SecondLevel extends Level {

    private static final String levelName = "level2";

    public SecondLevel() {
        super(loadWorldFromLvlPath(levelName), 1);
    }
}
