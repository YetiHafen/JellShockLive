package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class FourthLevel extends Level {

    private static final String levelName = "level_4";

    public FourthLevel() {
        super(loadWorldFromLvlPath(levelName), 1, 1);

        setBotPosition(0, 1500);
    }
}
