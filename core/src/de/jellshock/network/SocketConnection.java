package de.jellshock.network;

import com.badlogic.gdx.Gdx;
import de.jellshock.network.game.GameSocket;
import de.jellshock.network.lobby.LobbySocket;
import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.Getter;

import java.net.URI;

@Getter
public class SocketConnection {

    private final String hostname;
    private final int port;

    private LobbySocket lobby;
    private GameSocket game;

    public SocketConnection(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void init() {
        URI uri = URI.create("https://" + hostname + ":" + port);

        IO.Options options = IO.Options.builder()
                .build();
        // Set Credentials for authentication

        Socket socket = IO.socket(uri, options);
        socket.connect();
        Gdx.app.log("Server", "Successfully connected");

        //lobby = new LobbySocket(uri, options);
        //game = new GameSocket(uri, options);
    }
}
