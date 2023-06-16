package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class FirstLevel extends Level {

    private static final String levelName = "level_1";

    public FirstLevel() {
        super(loadWorldFromLvlPath(levelName), 1, 1);

        setBotPosition(0, 1500);
    }



}
