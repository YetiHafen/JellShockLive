package de.jellshock.game.screen.game.offline;

import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.World;

public abstract class OfflineScreen extends GameScreen {

    public OfflineScreen(World world) {
        super(world, "Test");
    }

    @Override
    public void show() {
    }

    @Override
    public void update(float delta) {
        if (shotProjectile != null) {
            shotProjectile.render(batch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
