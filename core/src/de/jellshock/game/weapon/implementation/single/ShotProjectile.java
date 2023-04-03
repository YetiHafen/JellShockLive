package de.jellshock.game.weapon.implementation.single;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import de.jellshock.JellShock;
import de.jellshock.game.weapon.Weapon;
import de.jellshock.game.weapon.WeaponType;
import de.jellshock.game.weapon.abstraction.SingleProjectile;

@Weapon(type = WeaponType.PROJECTILE, enabledByDefault = true)
public class ShotProjectile extends SingleProjectile {

    public ShotProjectile() {
        super("Shot", Color.CYAN);
        JellShock.getInstance().getWeaponManager().register(this);
        setGravity(400);
    }

    @Override
    protected void onMapCollision() {
        world.getMap().addCircleDamage((int) position.x, 200, 200);
    }

    @Override
    protected Texture getTexture() {
        return new Texture("shot.png");
    }
}
