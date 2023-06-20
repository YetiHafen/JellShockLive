package de.jellshock.game.player;

import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.hud.HealthBar;
import de.jellshock.game.vehicle.Tank;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Entity {

    protected Tank tank;

    @Setter
    private HealthBar healthBar;

    private int health = 100;

    public Entity(GameScreen gameScreen, Tank tank) {
        this.tank = tank;

        healthBar = new HealthBar(gameScreen, this);
    }

    public void setHealth(int health) {
        if (health > 100) health = 100;
        if (health < 0) health = 0;
        this.health = health;
    }
}
