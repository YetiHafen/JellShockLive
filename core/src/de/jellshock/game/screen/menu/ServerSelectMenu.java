package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.network.lobby.LobbySocket;
import io.socket.client.IO;

import java.net.URI;
import java.util.List;

public class ServerSelectMenu extends AbstractMenuScreen {

    private final LobbySocket lobbySocket;

    private final Stage stage;
    private final Table table;

    private final Skin skin;

    public ServerSelectMenu() {
        lobbySocket = new LobbySocket(URI.create(Constants.SERVER_URL), IO.Options.builder().build(), this);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));

        table = new Table(skin);
        table.center();
        table.setFillParent(true);
        stage.addActor(table);

        Label serverIdLabel = new Label("Server ID", skin);
        Label serverMapLabel = new Label("Map", skin);
        Label serverPlayerCountLabel = new Label("Players", skin);
        Label serverGameStateLabel = new Label("GameState", skin);
        addToTable(serverIdLabel, serverMapLabel, serverPlayerCountLabel, serverGameStateLabel);

        new Thread(() -> {
            try {
                lobbySocket.connect();

            } catch (RuntimeException exception) {
                Gdx.app.error("Server", "Error while connecting to host");
                JellShock.getInstance().setScreen(new MenuScreen());
            }
        }).start();

        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
    }

    public void postServers(List<LobbySocket.Game> gameList) {
        for (LobbySocket.Game game : gameList) {
            System.out.println(game.getId());
            Label nameLabel = new Label(game.getId(), skin);
            Label mapLabel = new Label(game.getMap(), skin);
            Label playerCountLabel = new Label(String.valueOf(game.getPlayerCount()), skin);
            Label gameStateLabel = new Label(game.getGameState().getName(), skin);
            addToTable(nameLabel, mapLabel, playerCountLabel, gameStateLabel);
        }
    }

    public void addToTable(Actor... actors) {
        for (Actor actor : actors) {
            table.add(actor).pad(10).expandX();
        }
        table.row();
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        stage.draw();
    }
}
