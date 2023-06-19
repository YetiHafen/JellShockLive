package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;
import de.jellshock.game.weapon.implementation.multi.FiveBallProjectile;
import de.jellshock.game.weapon.implementation.single.ShotProjectile;

public class SecondLevel extends Level {

    public static final int level = 2;

    public SecondLevel() {
        super(loadWorldFromLvl(level), 2, 2);

        setBotPosition(0, 1500);
        setBotPosition(1, 2500);

        bots.get(0).registerWeapon(ShotProjectile.class);
        bots.get(1).registerWeapon(FiveBallProjectile.class);
    }
}
