package de.jellshock.game.weapon.implementation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import de.jellshock.game.weapon.Weapon;
import de.jellshock.game.weapon.WeaponType;
import de.jellshock.game.weapon.abstraction.SingleProjectile;

@Weapon(type = WeaponType.PROJECTILE)
public class TestProjectile extends SingleProjectile {

    public TestProjectile() {
        super("Test", Color.BLUE);
        setGravity(400);
    }

    @Override
    protected Texture getTexture() {
        return new Texture("hexenmeister.png");
    }
}
