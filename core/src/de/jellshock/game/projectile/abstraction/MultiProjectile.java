package de.jellshock.game.projectile.abstraction;

import com.badlogic.gdx.graphics.Texture;
import de.jellshock.game.projectile.ProjectileCategory;

import java.util.List;

public abstract class MultiProjectile extends Projectile {

    private final List<Texture> textures;

    public MultiProjectile(ProjectileCategory category, boolean enabledByDefault) {
        super(category, enabledByDefault);
        this.textures = collectTextures();
    }

    public abstract List<Texture> collectTextures();


}
