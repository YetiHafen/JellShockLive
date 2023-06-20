package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;
import de.jellshock.game.weapon.implementation.single.ShotProjectile;

public class FirstLevel extends Level {

    public static final int level = 1;

    public FirstLevel() {
        super(loadWorldFromLvl(level), 1, 1);

        player.registerWeapon(ShotProjectile.class);

        setBotPosition(0, 1500);
        bots.get(0).registerWeapon(ShotProjectile.class);

        menuBar.initPlayerWeapons(player);
    }
}
