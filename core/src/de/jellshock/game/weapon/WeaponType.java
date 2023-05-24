package de.jellshock.game.weapon;

public enum WeaponType {

    /**
     * Weapon effects activate immediately upon hitting the ground.
     */
    DIRECT_IMPACT,

    /**
     * Weapon isn't affected by gravity.
     * This also mean that this weapons can bypass landmasses or go straight to the target.
     */
    STRAIGHT_SHOT,

    /**
     * Weapon work like {{@link #DIRECT_IMPACT}} but doesn't do map damage.
     */
    PROJECTILE,

    /**
     * Weapon that does something upon hitting the ground.
     * The direct hit of the weapon doesn't do any damage.
     */
    IMPACT_EFFECT,

    /**
     * Weapon where the projectile splits into multiple child projectiles just before impact.
     */
    AIRBURST,

    /**
     * Weapon that will bounce on the ground and do not activate until they come to a rest.
     * This weapon has a bigger radius and can also include air strikes.
     */
    FLARES,

    /**
     * Weapon that is uncategorized
     */
    UNKNOWN

    // TODO Possible addition: ENEMY_IMPACT

}
