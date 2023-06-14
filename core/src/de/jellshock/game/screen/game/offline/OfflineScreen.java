package de.jellshock.game.screen.game.offline;

import de.jellshock.game.event.key.KeyEvent;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.world.World;

public abstract class OfflineScreen extends GameScreen {

    public OfflineScreen(World world) {
        super(world);
    }

    @Override
    public void update(float delta, KeyEvent event) {
        if (shotProjectile != null) {
            shotProjectile.render(batch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
