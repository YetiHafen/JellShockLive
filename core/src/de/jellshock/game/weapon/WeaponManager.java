package de.jellshock.game.weapon;

import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class WeaponManager {

    private final HashMap<WeaponType, AbstractWeapon> weapons;

    public WeaponManager() {
        weapons = new HashMap<>();
    }

    public void register(AbstractWeapon weapon) {
        if (weapons.containsValue(weapon)) return;
        if (!weapon.getClass().isAnnotationPresent(Weapon.class))
            throw new IllegalArgumentException(weapon.getClass() + " is not annotated with " + Weapon.class);

        Weapon annotation = weapon.getClass().getAnnotation(Weapon.class);
        weapons.put(annotation.type(), weapon);
    }
}
