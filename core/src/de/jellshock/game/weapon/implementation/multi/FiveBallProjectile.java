package de.jellshock.game.weapon.implementation.multi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.weapon.Weapon;
import de.jellshock.game.weapon.WeaponType;
import de.jellshock.game.weapon.abstraction.MultiProjectile;

@Weapon(type = WeaponType.DIRECT_IMPACT)
public class FiveBallProjectile extends MultiProjectile {

    public FiveBallProjectile() {
        super("FiveBall", Color.RED, 5, 12);
        setGravity(400);
    }

    @Override
    protected void onMapCollision(Vector2 position) {
        world.getMap().addCircleDamage((int) position.x, 0, 50);
    }

    @Override
    protected Texture getBaseTexture() {
        return JellShock.getInstance().getAssetManager().get(Constants.SHOT_PATH);
    }
}
