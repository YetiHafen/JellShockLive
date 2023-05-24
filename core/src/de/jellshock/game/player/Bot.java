package de.jellshock.game.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.vehicle.Tank;
import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import de.jellshock.game.world.World;
import lombok.Getter;

import java.util.HashSet;

@Getter
public class Bot implements Disposable {

    private final String name;
    private final Tank tank;
    private final World world;
    private final HashSet<AbstractWeapon> weapons;

    public Bot(String name, World world) {
        this.name = name;
        this.world = world;
        this.tank = new Tank(Color.RED, world);
        weapons = new HashSet<>();
    }

    public void randomSpawn() {
        tank.setPosition((float) (Math.random() * world.getMap().getMapWidth()));
    }

    @Override
    public void dispose() {
        tank.dispose();
    }
}
