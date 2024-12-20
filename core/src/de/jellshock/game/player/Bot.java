package de.jellshock.game.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.hud.HealthBar;
import de.jellshock.game.vehicle.Tank;
import de.jellshock.game.weapon.Weapon;
import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import de.jellshock.game.world.World;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Bot extends Entity implements Disposable {

    private final String name;
    private final World world;
    private Class<? extends AbstractWeapon> weapon;

    private int strength = 0;

    public Bot(GameScreen gameScreen, String name, World world) {
        super(gameScreen, new Tank(Color.RED, world));
        this.name = name;
        this.world = world;
        weapon = null;
    }

    public void registerWeapon(Class<? extends AbstractWeapon> weapon) {
        if (!weapon.isAnnotationPresent(Weapon.class))
            throw new IllegalArgumentException(weapon.getSimpleName() + " is not annotated with " + Weapon.class);
        if (this.weapon != null) return;
        this.weapon = weapon;
    }


    public void randomSpawn() {
        tank.setPosition(ThreadLocalRandom.current().nextFloat(200, 2800));
    }

    public int randomStrength() {
        return ThreadLocalRandom.current().nextInt(10, 100);
    }

    @Override
    public void dispose() {
        tank.dispose();
    }
}
