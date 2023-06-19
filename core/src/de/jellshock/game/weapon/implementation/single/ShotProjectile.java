package de.jellshock.game.weapon.implementation.single;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.player.Entity;
import de.jellshock.game.weapon.Weapon;
import de.jellshock.game.weapon.WeaponType;
import de.jellshock.game.weapon.abstraction.SingleProjectile;

import java.util.List;

@Weapon(type = WeaponType.PROJECTILE, enabledByDefault = true)
public class ShotProjectile extends SingleProjectile {

    public ShotProjectile() {
        super(Color.CYAN, 10);
        setGravity(400);
    }

    @Override
    protected void onMapCollision() {
        float y1 = world.getMap().getMapHeight((int) position.x);
        world.getMap().addCircleDamage((int) position.x, 0, 60);

        float x1 = position.x - (60 + 10);
        float x2 = position.x + (60 + 10);
        float y2 = world.getMap().getMapHeight((int) position.x);

        List<Entity> entities = gameScreen.getEntities();
        entities.forEach(entity -> {
            float pos = entity.getTank().getPosition();
            if (pos > x1 && pos < x2) {
                int height = world.getMap().getMapHeight((int) pos);
                if (height < y1 && height > y2) {
                    entity.setHealth(entity.getHealth() - damage);
                }
            }
        });
    }

    @Override
    protected Texture getTexture() {
        return JellShock.getInstance().getAssetManager().get(Constants.SHOT_PATH);
    }

    @Override
    public void dispose() {

    }
}
