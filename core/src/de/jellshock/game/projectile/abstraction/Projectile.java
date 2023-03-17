package de.jellshock.game.projectile.abstraction;

import com.badlogic.gdx.math.Vector2;
import de.jellshock.game.projectile.IProjectile;
import de.jellshock.game.projectile.ProjectileCategory;
import de.jellshock.game.world.World;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class Projectile implements IProjectile {

    protected ProjectileCategory category;
    private final boolean enabledByDefault;
    protected boolean enabled;

    protected String name;
    protected Color color;

    protected Vector2 position;
    protected Vector2 velocity;
    protected World world;

    protected float gravity = 9.81F;

    public Projectile(ProjectileCategory category) {
        this(category, false);
    }

    public Projectile(ProjectileCategory category, boolean enabledByDefault) {
        this.category = category;
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





}
