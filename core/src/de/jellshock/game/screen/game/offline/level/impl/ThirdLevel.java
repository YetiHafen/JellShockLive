package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class ThirdLevel extends Level {

    public static final int level = 3;

    public ThirdLevel() {
        super(loadWorldFromLvl(level), 3, 1);

        setBotPosition(0, 1000);
    }
}
