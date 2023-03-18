package de.jellshock.game.projectile.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import de.jellshock.game.projectile.IProjectile;
import de.jellshock.game.projectile.ProjectileCategory;
import de.jellshock.game.world.World;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Projectile implements IProjectile {

    protected ProjectileCategory category;
    protected final boolean enabledByDefault;
    protected boolean enabled;

    protected String name;
    protected Color color;

    protected Vector2 position;
    protected Vector2 velocity;
    protected World world;

    protected float gravity = 9.81F;

    public Projectile(String name, Color color) {
        this(name, color, false);
    }

    public Projectile(String name, Color color, boolean enabledByDefault) {
        this(name, color, ProjectileCategory.DEFAULT, false);
    }

    public Projectile(String name, Color color, ProjectileCategory category) {
        this(name, color, ProjectileCategory.DEFAULT, false);
    }

    public Projectile(String name, Color color, ProjectileCategory category, boolean enabledByDefault) {
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

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
