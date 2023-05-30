package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import de.jellshock.Constants;
import de.jellshock.network.Game;
import de.jellshock.network.lobby.LobbySocket;
import io.socket.client.IO;

import java.net.URI;
import java.util.List;

public class ServerSelectMenu extends AbstractMenuScreen {

    private final LobbySocket lobbySocket;
    private final Stage stage;
    private final Skin skin;

    private final Table listTable;

    public ServerSelectMenu() {
        super(false);
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.NEON_SKIN_PATH));

        lobbySocket = connect();

        Table table = new Table();
        table.setFillParent(true);

        listTable = new Table();
        listTable.center();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        listTable.setSize(width * 0.3f, height * 0.7f);
        table.add(listTable).padTop(20).expand().center().top().row();

        Label title = new Label("SERVER LIST", skin);
        title.setAlignment(Align.center);
        listTable.add(title).colspan(Game.getLabels().length).padTop(10).row();

        for (int i = 0; i < Game.getLabels().length; i++) {
            Label serverLabel = new Label(Game.getLabels()[i], skin);
            listTable.add(serverLabel).pad(10).center();
        }
        listTable.row();

        Table optionsTable = new Table();
        optionsTable.setSize(width * 0.5f, height * 0.2f);
        optionsTable.center();

        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
        TextButton createGame = new TextButton("Create Game", textButtonStyle);
        createGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("T");
            }
        });
        TextButton reload = new TextButton("Reload Server List", textButtonStyle);
        reload.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lobbySocket.reload();
                System.out.println("Reload");
            }
        });
        optionsTable.add(createGame).pad(10);
        optionsTable.add(reload).pad(10);
        table.row().padTop(20);
        table.add(optionsTable).center();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
    }

    public void postServers(List<Game> gameList) {
        for (Game game : gameList) {
            System.out.println(game.getGameId());
            Label gameIdLabel = new Label(game.getGameId(), skin);
            Label nameLabel = new Label(game.getName(), skin);
            Label mapLabel = new Label(game.getMap(), skin);
            Label playerCountLabel = new Label(String.valueOf(game.getPlayerCount()), skin);
            Label gameStateLabel = new Label(game.getGameState().getName(), skin);
            listTable.add(gameIdLabel, nameLabel, mapLabel, playerCountLabel, gameStateLabel).pad(10).center().row();
        }
    }

    public LobbySocket connect() {
        LobbySocket socket = new LobbySocket(URI.create(Constants.SERVER_URL), IO.Options.builder().build(), this);
        new Thread(socket::connect).start();
        return socket;
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
