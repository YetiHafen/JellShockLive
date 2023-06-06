package de.jellshock.network.lobby;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import de.jellshock.JellShock;
import de.jellshock.game.screen.menu.CreateAccountMenu;
import de.jellshock.game.screen.menu.ServerSelectMenu;
import de.jellshock.game.util.DialogUtils;
import de.jellshock.network.AbstractSocket;
import de.jellshock.network.Game;
import de.jellshock.network.Maps;
import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.Getter;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Getter
public class LobbySocket extends AbstractSocket {

    private static final Gson gson = new Gson();
    private static final int RELOAD_TIMEOUT = 1000;
    private long lastReload;
    private final List<Game> games;
    private final ServerSelectMenu menu;

    public LobbySocket(URI uri, IO.Options options, ServerSelectMenu menu) {
        super(uri, options, "");
        games = new ArrayList<>();
        this.menu = menu;
    }

    @Override
    public void onConnection(Socket socket) {

        socket.on("list", args -> {
            games.clear();
            for (int i = 0; i < args.length; i++) {
                JSONObject obj = (JSONObject) args[i];
                Game game = gson.fromJson(obj.toString(), Game.class);
                games.add(game);
            }
            Gdx.app.postRunnable(() -> {
                menu.clearList();
                menu.postServers(games);
            });
        });

        socket.on("err", args -> {
            Gdx.app.postRunnable(() -> {
                DialogUtils.error("Error while fetching user. Try again", menu.getStage(), menu.getSkin());
            });
        });

        // Keep alive reload
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                socket.emit("reload");
            }
        }, 0, 4 * 1000);
    }

    public void joinGame(String gameId, String name) {
        socket.emit("join", gameId);
    }

    public void createGame(String serverName, String password, Maps map, int maxPlayers) {
        JSONObject game = new JSONObject()
                .put("name", serverName)
                .put("password", password)
                .put("map", map.getName())
                .put("maxPlayers", maxPlayers)
                .put("admin", JellShock.getInstance().getScreen(CreateAccountMenu.class).getName());

        socket.emit("create", game);
    }

    public void reload() {
        if ((System.currentTimeMillis() - lastReload) < RELOAD_TIMEOUT) return;
        lastReload = System.currentTimeMillis();
        socket.emit("reload");
    }

}
