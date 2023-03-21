package de.jellshock.game.weapon;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class WeaponManager {

    private final HashMap<WeaponType, IWeapon> weapons;

    public WeaponManager() {
        weapons = new HashMap<>();
    }

    public void register(IWeapon weapon) {
        if (weapons.containsValue(weapon)) return;
        WeaponType weaponType = WeaponType.UNKNOWN;
        if (weapon.getClass().isAnnotationPresent(Weapon.class)) {
             weaponType = weapon.getClass().getAnnotation(Weapon.class).type();
        }
        weapons.put(weaponType, weapon);
    }

    public static boolean isAnno(IWeapon weapon) {
        return weapon.getClass().isAnnotationPresent(Weapon.class);
    }
}
