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

import java.util.HashSet;

@Getter
public class Player extends Entity implements KeyEventListener, Disposable {

    private final String name;
    // TODO: Elo
    private Team team;
    private HashSet<Class<? extends AbstractWeapon>> weapons;

    private final HealthBar healthBar;

    private int health = 100;
    private int strength = 0;
    private int tankCapacity = 500;

    public Player(GameScreen gameScreen, String name, World world) {
        super(new Tank(Color.CYAN, world));
        this.name = name;
        weapons = new HashSet<>();
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
            case MOVE_LEFT -> tank.moveX(-100 * delta);
            case MOVE_RIGHT -> tank.moveX(100 * delta);
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

    public void setHealth(int health) {
        if (health > 100) return;
        this.health = health;
    }

    public void setStrength(int strength) {
        if (strength > 100) return;
        this.strength = strength;
    }

    @Override
    public void dispose() {
        tank.dispose();
    }
}