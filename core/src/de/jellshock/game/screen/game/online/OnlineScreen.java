package de.jellshock.game.screen.game.online;

import de.jellshock.Constants;
import de.jellshock.game.event.key.KeyEvent;
import de.jellshock.game.player.Player;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.World;
import de.jellshock.network.game.GameSocket;
import io.socket.client.IO;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OnlineScreen extends GameScreen {

    private final List<Player> onlinePlayers;

    private GameSocket gameSocket;

    @Setter
    private String name;
    @Setter
    private String password;

    public OnlineScreen() {
        super(new World("Test", MapType.MOUNTAIN));
        world.generateWorld();
        onlinePlayers = new ArrayList<>();
    }

    @Override
    public void update(float delta, KeyEvent event) {
        /*
        switch (event.getType()) {

        }*/
        if (shotProjectile != null) {
            shotProjectile.render(batch);
        }
    }

    public void addPlayer(Player player) {
        onlinePlayers.add(player);
        renderObjects.add(player.getTank());
    }

    public void removePlayer(Player player) {
        onlinePlayers.remove(player);
        renderObjects.remove(player.getTank());
    }

    public void connect(String gameId) {
        if (gameSocket != null) return;
        gameSocket = new GameSocket(URI.create(Constants.SERVER_URL), gameId, IO.Options.builder().build(), this);
        new Thread(gameSocket::connect).start();
    }

    @Override
    public void dispose() {
        gameSocket.close();
        onlinePlayers.forEach(Player::dispose);
        super.dispose();
    }
}
