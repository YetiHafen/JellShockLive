package de.jellshock.network.game;

import com.badlogic.gdx.Gdx;
import de.jellshock.JellShock;
import de.jellshock.game.screen.game.online.OnlineScreen;
import de.jellshock.game.screen.menu.ServerSelectMenu;
import de.jellshock.game.util.DialogUtils;
import de.jellshock.network.AbstractSocket;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;

public class GameSocket extends AbstractSocket {

    private final OnlineScreen onlineScreen;

    public GameSocket(URI uri, String gameId, IO.Options options, OnlineScreen online) {
        super(uri, options, "/" + gameId);
        this.onlineScreen = online;
    }

    @Override
    public void onConnection(Socket socket) {
        socket.emit("join", onlineScreen.getName());

        socket.on("err", args -> {
            Gdx.app.postRunnable(() -> {
                ServerSelectMenu menu = JellShock.getInstance().setScreen(ServerSelectMenu.class);
                DialogUtils.error("Error while fetching user. Try again", menu.getStage(), menu.getSkin());
            });
        });
    }
}