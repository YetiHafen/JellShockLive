package de.jellshock.network.game;

import com.badlogic.gdx.Gdx;
import de.jellshock.JellShock;
import de.jellshock.game.player.Player;
import de.jellshock.game.player.Team;
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
        socket.emit(Package.JOIN.getValue(), onlineScreen.getName());

        socket.on(Package.JOIN_DATA.getValue(), args -> {
            onlineScreen.getPlayer().setTeam(Team.getTeamByPackage((int) args[0]));
            onlineScreen.getPlayer().getTank().setPosition((int) args[1]);
        });

        socket.on(Package.JOIN.getValue(), args -> {
            Team team = Team.getTeamByPackage((int) args[1]);
            if (team == null) return;
            Player player = new Player(onlineScreen, (String) args[0], team, onlineScreen.getWorld());
            player.getTank().setPosition((int) args[2]);
            onlineScreen.addPlayer(player);
        });

        socket.on(Package.LEFT.getValue(), args -> {
            Player player = onlineScreen.getOnlinePlayers()
                    .stream()
                    .filter(p -> p.getName() == args[0])
                    .findAny().orElseThrow();
            onlineScreen.removePlayer(player);
        });

        socket.on(Package.MOVEMENT.getValue(), args -> {

        });

        socket.on(Package.ERROR.getValue(), args -> Gdx.app.postRunnable(() -> {
            ServerSelectMenu menu = JellShock.getInstance().setScreen(ServerSelectMenu.class);
            DialogUtils.error("Error while fetching user. Try again", menu.getStage(), menu.getSkin());
        }));

        /*socket.on(Socket.EVENT_CONNECT_ERROR, args -> Gdx.app.postRunnable(() -> {
            ServerSelectMenu menu = JellShock.getInstance().setScreen(ServerSelectMenu.class);
            DialogUtils.error("Server side issue occurred", menu.getStage(), menu.getSkin());
        }));*/

        socket.on(Socket.EVENT_DISCONNECT, args -> Gdx.app.postRunnable(() -> {
            ServerSelectMenu menu = JellShock.getInstance().setScreen(ServerSelectMenu.class);
            DialogUtils.error("Server closed", menu.getStage(), menu.getSkin());
        }));
    }

    public void emitMoveEvent(int value) {
        socket.emit(Package.MOVEMENT.getValue(), value);
    }

    public void updatePosition(int x) {
        socket.emit("pos", x);
    }
}