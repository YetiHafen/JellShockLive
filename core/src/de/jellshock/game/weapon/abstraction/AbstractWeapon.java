package de.jellshock.game.weapon.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import de.jellshock.game.weapon.IWeapon;
import de.jellshock.game.world.World;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractWeapon implements IWeapon {

    protected final boolean enabledByDefault;
    protected boolean enabled;
    protected String name;

    protected Color color;
    protected World world;

    protected int damage;
    protected int damageRadius;

    // projectile position
    protected Vector2 position;
    protected Vector2 velocity;

    protected float gravity = 9.81F;


    public AbstractWeapon(String name, Color color) {
        this(name, color, false);
    }

    public AbstractWeapon(String name, Color color, boolean enabledByDefault) {
        this.name = name;
        this.color = color;
        this.enabledByDefault = enabledByDefault;
        setEnabled(enabledByDefault);
    }

    public void update(float delta) {
        velocity.y -= gravity * delta;

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
