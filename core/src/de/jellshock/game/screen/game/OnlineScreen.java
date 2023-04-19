package de.jellshock.game.screen.game;

import de.jellshock.game.player.Player;
import de.jellshock.game.world.MapType;

import java.util.ArrayList;
import java.util.List;

public class OnlineScreen extends GameScreen {

    private final List<Player> onlinePlayers;

    public OnlineScreen() {
        super("", MapType.MOUNTAIN, "");
        onlinePlayers = new ArrayList<>();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
