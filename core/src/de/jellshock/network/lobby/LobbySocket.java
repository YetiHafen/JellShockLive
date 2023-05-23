package de.jellshock.network.lobby;

import com.badlogic.gdx.Gdx;
import de.jellshock.network.AbstractSocket;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;

public class LobbySocket extends AbstractSocket {

    private long lastReload;
    private static final int reloadTimeout = 1000;

    public LobbySocket(URI uri, IO.Options options) {
        super(uri, options, "/");
    }

    @Override
    public void onConnection() {
        socket.on(Socket.EVENT_CONNECT, args -> {
            Gdx.app.log("Server", "Connected to " + uri.getHost());
        });
        socket.on(Socket.EVENT_DISCONNECT, args -> {
            Gdx.app.log("Server", "Disconnected from host");
        });
        socket.on("serverlist", System.out::println);
    }

    public void reload() {
        if ((System.currentTimeMillis() - lastReload) < reloadTimeout) return;
        lastReload = System.currentTimeMillis();

        socket.emit("reload", "1");
    }
}
