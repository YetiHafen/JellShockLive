package de.jellshock.game.screen.game.online;

import de.jellshock.Constants;
import de.jellshock.game.player.Player;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.World;
import de.jellshock.network.game.GameSocket;
import io.socket.client.IO;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class OnlineScreen extends GameScreen {

    private final List<Player> onlinePlayers;

    private final GameSocket gameSocket;

    public OnlineScreen(String gameId) {
        super(new World("Test", MapType.MOUNTAIN), "Test");
        onlinePlayers = new ArrayList<>();

        gameSocket = connect(gameId);
    }

    @Override
    public void update(float delta) {
        if (shotProjectile != null) {
            shotProjectile.render(batch);
        }
    }

    public GameSocket connect(String gameId) {
        GameSocket socket = new GameSocket(URI.create(Constants.SERVER_URL), gameId, IO.Options.builder().build(), this);
        new Thread(socket::connect).start();
        return socket;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
