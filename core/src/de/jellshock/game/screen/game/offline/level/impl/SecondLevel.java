package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;
import de.jellshock.game.weapon.implementation.multi.FiveBallProjectile;
import de.jellshock.game.weapon.implementation.single.ShotProjectile;

public class SecondLevel extends Level {

    public static final int level = 2;

    public SecondLevel() {
        super(loadWorldFromLvl(level), 2, 2);

        player.registerWeapon(ShotProjectile.class);
        player.registerWeapon(FiveBallProjectile.class);

        bots.get(0).registerWeapon(ShotProjectile.class);

        setBotPosition(0, 1500);
        setBotPosition(1, 2500);

        menuBar.initPlayerWeapons(player);
    }
}
