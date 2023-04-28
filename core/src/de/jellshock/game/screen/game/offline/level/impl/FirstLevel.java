package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class FirstLevel extends Level {

    private static final String levelName = "test";

    public FirstLevel() {
        super(loadWorldFromLvlPath(levelName), 5);
    }



}
