package de.jellshock.network;

import io.socket.client.IO;
import io.socket.client.Socket;
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

    public void connect() {
        URI nameSpaceUri = URI.create(uri.toString() + namespace);
        socket = IO.socket(nameSpaceUri, options);
        onConnection();
    }

    public abstract void onConnection();

    public boolean isConnected() {
        return socket.connected();
    }
}
