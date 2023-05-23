package de.jellshock.network.game;

import de.jellshock.network.AbstractSocket;
import io.socket.client.IO;

import java.net.URI;

public class GameSocket extends AbstractSocket {

    public GameSocket(URI uri, IO.Options options) {
        super(uri, options, "game");
    }

    @Override
    public void onConnection() {

    }
}
