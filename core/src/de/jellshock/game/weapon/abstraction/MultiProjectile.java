package de.jellshock.game.weapon.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import de.jellshock.game.weapon.Weapon;
import de.jellshock.game.weapon.WeaponType;

import java.util.List;

public abstract class MultiProjectile extends AbstractWeapon {

    protected final List<Texture> textures;

    public MultiProjectile(String name, Color color) {
        this(name, color, false);
    }

    public MultiProjectile(String name, Color color, boolean enabledByDefault) {
        super(name, color, enabledByDefault);
        this.textures = getTextures();
    }

    protected abstract List<Texture> getTextures();


}
