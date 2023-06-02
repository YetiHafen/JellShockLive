package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import de.jellshock.Constants;
import de.jellshock.game.util.DialogUtils;
import de.jellshock.network.Game;
import de.jellshock.network.Maps;
import de.jellshock.network.lobby.LobbySocket;
import io.socket.client.IO;

import java.net.URI;
import java.util.List;

public class ServerSelectMenu extends AbstractMenuScreen {

    private final LobbySocket lobbySocket;
    private final Skin skin;
    private final Texture joinButtonTexture;
    private final Table listTable;

    public ServerSelectMenu() {
        super(false);
        stage = new Stage();
        skin = new Skin(Gdx.files.internal(Constants.NEON_SKIN_PATH));

        joinButtonTexture = new Texture(Gdx.files.internal("menu/play.png"));

        lobbySocket = connect();

        Table table = new Table();
        table.setFillParent(true);

        listTable = new Table();
        listTable.center();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        listTable.setSize(width * 0.3f, height * 0.7f);
        table.add(listTable).padTop(20).expand().center().top().row();

        serverListTable();

        Table optionsTable = new Table();
        optionsTable.setSize(width * 0.5f, height * 0.2f);
        optionsTable.center();

        TextButton.TextButtonStyle textButtonStyle = skin.get(TextButton.TextButtonStyle.class);
        TextButton createGame = new TextButton("Create Game", textButtonStyle);
        createGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openCreateGamePopUp();
            }
        });
        optionsTable.add(createGame).pad(10);
        TextButton reload = new TextButton("Reload Server List", textButtonStyle);
        reload.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lobbySocket.reload();
            }
        });
        optionsTable.add(reload).pad(10);

        table.row().padTop(20);
        table.add(optionsTable).center();

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);

        backButtonTexture = new Texture("menu/left-arrow.png");
        ImageButton button = new ImageButton(new TextureRegionDrawable(backButtonTexture));
        button.setSize(30, 30);
        button.setPosition(10, Gdx.graphics.getHeight() - 50);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setVisible(false);
                setSlideScreen(new MenuScreen(), Direction.LEFT);
            }
        });
        stage.addActor(button);
    }

    public void serverListTable() {
        Label title = new Label("SERVER LIST", skin);
        title.setAlignment(Align.center);
        listTable.add(title).colspan(Game.getLabels().length + 1).padTop(10).row();

        Label spaceLabel = new Label("Join", skin);
        listTable.add(spaceLabel).pad(10).center();
        for (int i = 0; i < Game.getLabels().length; i++) {
            Label serverLabel = new Label(Game.getLabels()[i], skin);
            listTable.add(serverLabel).pad(10).center();
        }
        listTable.row();
    }

    public void postServers(List<Game> gameList) {
        // ConcurrentModificationException ka woher die kommt. Will das jetzt nicht fixen
        for (Game game : gameList) {
            Image joinImage = new Image(joinButtonTexture);
            joinImage.setScaling(Scaling.fit);

            joinImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Joined " + game.getGameId());
                }
            });
            listTable.add(joinImage).height(30).width(30);

            Label gameIdLabel = new Label(game.getGameId(), skin);
            Label nameLabel = new Label(game.getName(), skin);
            Label mapLabel = new Label(game.getMap().getName(), skin);
            Label playerCountLabel = new Label(game.getPlayerCount() + " / " + game.getMaxPlayers(), skin);
            Label gameStateLabel = new Label(game.getGameState().getName(), skin);

            listTable.add(gameIdLabel, nameLabel, mapLabel, playerCountLabel, gameStateLabel).pad(10).center().row();
        }
    }

    public void clearList() {
        listTable.clear();
        serverListTable();
    }

    public void openCreateGamePopUp() {
        Label serverLabel = new Label("Servername*: ", skin);
        TextField serverField = new TextField("", skin);

        Label passwordLabel = new Label("Password:" , skin);
        TextField passwordField = new TextField("", skin);

        Label mapLabel = new Label("Map:", skin);
        SelectBox<Maps> mapSelect = new SelectBox<>(skin);
        mapSelect.setItems(Maps.values());

        Label maxPlayersLabel = new Label("Max Players:", skin);
        SelectBox<Integer> maxPlayerSelect = new SelectBox<>(skin);
        maxPlayerSelect.setItems(2, 4, 6, 8, 10);

        Dialog dialog = new Dialog("Create Game", skin) {
            @Override
            protected void result(Object object) {
                if ((Boolean) object) {
                    if (serverField.getText().isEmpty()) {
                        DialogUtils.error("The Server Name can't be empty", stage, skin);
                        return;
                    }
                    if (passwordField.getText().length() > 12) {
                        DialogUtils.error("Password can not be longer than 12 characters", stage, skin);
                        return;
                    }
                    lobbySocket.createGame(serverField.getText(), passwordField.getText(), mapSelect.getSelected(), maxPlayerSelect.getSelected());
                }
            }
        };
        dialog.setModal(true);
        dialog.setMovable(false);
        dialog.setResizable(false);

        dialog.getContentTable().add(serverLabel);
        dialog.getContentTable().add(serverField).row();
        dialog.getContentTable().add(passwordLabel);
        dialog.getContentTable().add(passwordField).row();
        dialog.getContentTable().add(mapLabel);
        dialog.getContentTable().add(mapSelect).row();
        dialog.getContentTable().add(maxPlayersLabel);
        dialog.getContentTable().add(maxPlayerSelect);

        dialog.button("Create", true).button("Cancel", false).key(Input.Keys.ENTER, true).key(Input.Keys.ESCAPE, false);
        dialog.show(stage);
        stage.setKeyboardFocus(serverField);
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
    public void dispose() {
        joinButtonTexture.dispose();
        skin.dispose();
        stage.dispose();
    }
}
