package de.jellshock.game.projectile;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ProjectileManager {

    private final List<IProjectile> projectiles = new LinkedList<>();

    public void register(IProjectile projectile) {
        projectiles.add(projectile);
    }

    public void unregister(IProjectile projectile) {
        projectiles.remove(projectile);
    }

    public void unregisterIf(Predicate<IProjectile> projectilePredicate) {
        projectiles.removeIf(projectilePredicate);
    }
}
