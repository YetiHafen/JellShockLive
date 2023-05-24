package de.jellshock.game.weapon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Weapon {
    WeaponType type() default WeaponType.UNKNOWN;

    boolean enabledByDefault() default false;
}
