package de.jellshock.game.screen.game.online;

import de.jellshock.game.player.Player;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.World;

import java.util.ArrayList;
import java.util.List;

public class OnlineScreen extends GameScreen {

    private final List<Player> onlinePlayers;

    public OnlineScreen() {
        super(new World("Test", MapType.MOUNTAIN), "Test");
        onlinePlayers = new ArrayList<>();
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
