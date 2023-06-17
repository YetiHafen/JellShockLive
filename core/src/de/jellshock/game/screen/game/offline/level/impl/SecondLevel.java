package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class SecondLevel extends Level {

    public static final int level = 2;

    public SecondLevel() {
        super(loadWorldFromLvl(level), 2, 1);
    }
}
