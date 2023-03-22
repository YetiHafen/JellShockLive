package de.jellshock.game.player;

import com.badlogic.gdx.graphics.Color;
import de.jellshock.game.vehicle.Tank;
import de.jellshock.game.world.World;
import lombok.Getter;

@Getter
public class Player {

    private final String name;
    // TODO: Elo
    private final Tank tank;

    public Player(String name, World world) {
        this.name = name;
        tank = new Tank(Color.CYAN, world);
    }


}
