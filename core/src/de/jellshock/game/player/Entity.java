package de.jellshock.game.player;

import de.jellshock.game.vehicle.Tank;
import lombok.Getter;

@Getter
public abstract class Entity {

    protected Tank tank;

    private int health = 100;

    public Entity(Tank tank) {
        this.tank = tank;
    }

    public void setHealth(int health) {
        if (health > 100) return;
        this.health = health;
    }
}
