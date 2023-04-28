package de.jellshock.game.screen.game.offline;

import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.world.MapType;

public abstract class OfflineScreen extends GameScreen {

    public OfflineScreen() {
        super("Random World", MapType.MOUNTAIN, "Test");
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
