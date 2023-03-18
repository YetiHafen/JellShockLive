package de.jellshock.game.projectile.abstraction;

import com.badlogic.gdx.graphics.Color;
import de.jellshock.game.projectile.ProjectileCategory;

public abstract class SlashProjectile extends SingleProjectile {

    public SlashProjectile(String name, Color color, ProjectileCategory category) {
        this(name, color, category, false);
    }

    public SlashProjectile(String name, Color color, ProjectileCategory category, boolean enabledByDefault) {
        super(name, color, category, enabledByDefault);
    }
}
