package de.jellshock.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.event.key.KeyEvent;
import de.jellshock.game.event.key.KeyEventListener;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.hud.HealthBar;
import de.jellshock.game.vehicle.Tank;
import de.jellshock.game.weapon.Weapon;
import de.jellshock.game.weapon.WeaponType;
import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import de.jellshock.game.world.World;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
public class Player extends Entity implements KeyEventListener, Disposable {

    private final String name;
    // TODO: Elo
    private Team team;
    private List<Class<? extends AbstractWeapon>> weapons;

    private final HealthBar healthBar;

    private int strength = 0;

    public static final int START_TANK_VALUE = 500;
    private int fuel = START_TANK_VALUE;

    public Player(GameScreen gameScreen, String name, World world) {
        super(new Tank(Color.CYAN, world));
        this.name = name;
        weapons = new ArrayList<>();
        team = Team.DEFAULT;

        healthBar = new HealthBar(gameScreen, this);
    }

    // Online players
    public Player(GameScreen gameScreen, String name, Team team, World world) {
        super(new Tank(team.getColor(), world));
        this.name = name;
        this.team = team;

        healthBar = new HealthBar(gameScreen, this);
    }

    public void registerWeapon(Class<? extends AbstractWeapon> weapon) {
        if (weapons.contains(weapon)) return;
        if (!weapon.isAnnotationPresent(Weapon.class))
            throw new IllegalArgumentException(weapon.getSimpleName() + " is not annotated with " + Weapon.class);

        weapons.add(weapon);
    }

    public WeaponType getWeaponType(Class<? extends AbstractWeapon> weapon) {
        return weapon.getAnnotation(Weapon.class).type();
    }

    public boolean isWeaponDefault(Class<? extends AbstractWeapon> weapon) {
        return weapon.getAnnotation(Weapon.class).enabledByDefault();
    }

    @Override
    public void handleKeyEvent(KeyEvent event) {
        float delta = Gdx.graphics.getDeltaTime();
        switch (event.getType()) {
            case MOVE_LEFT -> {
                if (fuel == 0) return;
                tank.moveX(-100 * delta);
            }
            case MOVE_RIGHT -> {
                if (fuel == 0) return;
                tank.moveX(100 * delta);
            }
            case GUN_ROTATION_LEFT -> tank.setGunRotation(tank.getGunRotation() + 100 * delta);
            case GUN_ROTATION_RIGHT -> tank.setGunRotation(tank.getGunRotation() - 100 * delta);
            case GUN_POWER_UP -> {
                if (strength == 100) return;
                strength++;
            }
            case GUN_POWER_DOWN -> {
                if (strength == 1) return;
                strength--;
            }
        }
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setStrength(int strength) {
        if (strength > 100) return;
        this.strength = strength;
    }

    public void setFuel(int fuel) {
        if (fuel > START_TANK_VALUE || fuel < 0) return;
        this.fuel = fuel;
    }

    @Override
    public void dispose() {
        tank.dispose();
    }
}