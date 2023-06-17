package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;

public class FourthLevel extends Level {

    public static final int level = 4;

    public FourthLevel() {
        super(loadWorldFromLvl(level), 1, 1);

        setBotPosition(0, 1500);
    }
}
