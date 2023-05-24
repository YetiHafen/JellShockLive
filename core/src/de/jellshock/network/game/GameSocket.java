package de.jellshock.network.game;

import com.google.gson.Gson;
import de.jellshock.network.AbstractSocket;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameSocket extends AbstractSocket {

    public GameSocket(URI uri, IO.Options options) {
        super(uri, options, "game");
    }

    @Override
    public void onConnection(Socket socket) {

    }
}