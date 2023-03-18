package de.jellshock.game.projectile.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import de.jellshock.game.projectile.ProjectileCategory;

import java.util.List;

public abstract class MultiProjectile extends Projectile {

    protected final List<Texture> textures;

    public MultiProjectile(String name, Color color, ProjectileCategory category) {
        this(name, color, category, false);
    }

    public MultiProjectile(String name, Color color, ProjectileCategory category, boolean enabledByDefault) {
        super(name, color, category, enabledByDefault);
        this.textures = getTextures();
    }

    protected abstract List<Texture> getTextures();


}
