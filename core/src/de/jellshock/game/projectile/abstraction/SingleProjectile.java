package de.jellshock.game.projectile.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import de.jellshock.game.projectile.ProjectileCategory;

public abstract class SingleProjectile extends Projectile {

    protected Texture texture;

    public SingleProjectile(String name, Color color, ProjectileCategory category) {
        this(name, color, category, false);
    }

    public SingleProjectile(String name, Color color, ProjectileCategory category, boolean enabledByDefault) {
        super(name, color, category, enabledByDefault);
        this.texture = getTexture();
    }

    protected abstract Texture getTexture();
}
