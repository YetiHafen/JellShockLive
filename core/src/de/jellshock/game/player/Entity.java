package de.jellshock.game.player;

import de.jellshock.game.vehicle.Tank;
import lombok.Getter;

@Getter
public abstract class Entity {

    protected Tank tank;

    public Entity(Tank tank) {
        this.tank = tank;
    }
}
