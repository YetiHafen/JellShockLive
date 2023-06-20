package de.jellshock.game.screen.game.offline.level.impl;

import de.jellshock.game.screen.game.offline.level.Level;
import de.jellshock.game.weapon.implementation.multi.FiveBallProjectile;
import de.jellshock.game.weapon.implementation.single.ShotProjectile;

public class ThirdLevel extends Level {

    public static final int level = 3;

    public ThirdLevel() {
        super(loadWorldFromLvl(level), 3, 3);

        player.registerWeapon(ShotProjectile.class);
        player.registerWeapon(FiveBallProjectile.class);

        bots.get(0).registerWeapon(ShotProjectile.class);
        bots.get(1).registerWeapon(FiveBallProjectile.class);

        setBotPosition(0, 1000);

        menuBar.initPlayerWeapons(player);
    }
}
