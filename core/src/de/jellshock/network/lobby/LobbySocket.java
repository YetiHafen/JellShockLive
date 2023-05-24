package de.jellshock.network.lobby;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import de.jellshock.game.screen.game.GameState;
import de.jellshock.game.screen.menu.ServerSelectMenu;
import de.jellshock.network.AbstractSocket;
import io.socket.client.IO;
import io.socket.client.Socket;
import lombok.Getter;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println(args[0]);
            games.clear();
            for (int i = 0; i < args.length; i++) {
                JSONObject obj = (JSONObject) args[i];
                Game game = gson.fromJson(obj.toString(), Game.class);
                games.add(game);
            }
            Gdx.app.postRunnable(() -> {
                menu.postServers(games);
            });
        });
    }

    public void reload() {
        if ((System.currentTimeMillis() - lastReload) < RELOAD_TIMEOUT) return;
        lastReload = System.currentTimeMillis();

        socket.emit("reload", "1");
    }


    public static class Game {
        @Getter
        private String id;
        @Getter
        private String map;
        @Getter
        private int playerCount;
        private int gameState;

        public GameState getGameState() {
            return GameState.getById(gameState);
        }
    }

}
