package de.jellshock.game.weapon.implementation.single;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import de.jellshock.game.weapon.Weapon;
import de.jellshock.game.weapon.WeaponType;
import de.jellshock.game.weapon.abstraction.SingleProjectile;

@Weapon(type = WeaponType.PROJECTILE)
public class ShotProjectile extends SingleProjectile {

    public ShotProjectile() {
        super("Shot", Color.CYAN);
        setGravity(400);
    }

    @Override
    protected Texture getTexture() {
        return new Texture("shot.png");
    }

    @Override
    public void dispose() {
    }
}
