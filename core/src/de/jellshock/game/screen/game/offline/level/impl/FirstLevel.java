package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class FirstLevel extends Level {

    public static final int level = 1;

    public FirstLevel() {
        super(loadWorldFromLvl(level), 1, 1);

        setBotPosition(0, 1500);
    }
}
