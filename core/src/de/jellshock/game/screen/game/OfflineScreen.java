package de.jellshock.game.screen.game;

import com.badlogic.gdx.utils.Array;
import de.jellshock.game.player.Player;
import de.jellshock.game.world.MapType;

public class OfflineScreen extends GameScreen {

    private final Array<Player> enemy;

    public OfflineScreen() {
        super("Random World", MapType.MOUNTAIN, "Test");
        enemy = new Array<>();
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
