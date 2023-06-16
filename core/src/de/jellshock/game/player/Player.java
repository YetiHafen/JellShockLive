package de.jellshock.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.event.key.KeyEvent;
import de.jellshock.game.event.key.KeyEventListener;
import de.jellshock.game.vehicle.Tank;
import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import de.jellshock.game.world.World;
import lombok.Getter;

import java.util.HashSet;

@Getter
public class Player implements KeyEventListener, Disposable {

    private final String name;
    // TODO: Elo
    private final Tank tank;
    private Team team;
    private HashSet<AbstractWeapon> weapons;
    private int health;

    public Player(String name, World world) {
        this.name = name;
        tank = new Tank(Color.CYAN, world);
        weapons = new HashSet<>();
        team = Team.DEFAULT;
    }

    // Online players
    public Player(String name, Team team, World world) {
        this.name = name;
        this.tank = new Tank(team.getColor(), world);
        this.team = team;
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        float delta = Gdx.graphics.getDeltaTime();
        switch (event.getType()) {
            case MOVE_LEFT -> tank.moveX(-100 * delta);
            case MOVE_RIGHT -> tank.moveX(100 * delta);
            case GUN_ROTATION_LEFT -> tank.setGunRotation(tank.getGunRotation() + 100 * delta);
            case GUN_ROTATION_RIGHT -> tank.setGunRotation(tank.getGunRotation() - 100 * delta);
        }
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public void dispose() {
        tank.dispose();
    }
}