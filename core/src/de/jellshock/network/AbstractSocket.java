package de.jellshock.network;

import com.badlogic.gdx.Gdx;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.client.SocketIOException;
import lombok.Getter;

import java.net.URI;

@Getter
public abstract class AbstractSocket {

    protected final URI uri;
    private final IO.Options options;
    protected final String namespace;

    protected Socket socket;

    public AbstractSocket(URI uri, IO.Options options, String namespace) {
        this.uri = uri;
        this.options = options;
        this.namespace = namespace;
    }

    public void connect() throws RuntimeException {
        URI nameSpaceUri = URI.create(uri.toString() + namespace);
        socket = IO.socket(nameSpaceUri, options);
        socket.connect();

        socket.on(Socket.EVENT_CONNECT, args -> {
            Gdx.app.log("Server - " + getClass().getSimpleName(), "Connected to " + uri.getHost());
        });
        socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            throw new RuntimeException(new SocketIOException("Error connecting to server. Reason: " + args[0]));
        });
        socket.on(Socket.EVENT_DISCONNECT, args -> {
            Gdx.app.log("Server - " + getClass().getSimpleName(), "Disconnected from host");
        });

        onConnection(socket);
    }

    public abstract void onConnection(Socket socket);

    public boolean isConnected() {
        return socket.connected();
    }
}
