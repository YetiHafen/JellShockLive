package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.util.DialogUtils;
import de.jellshock.network.lobby.AccountSocket;
import io.socket.client.IO;
import lombok.Getter;

import java.net.URI;

@Getter
public class CreateAccountMenu extends AbstractMenuScreen {
    private final Skin skin;
    private String name;
    private String password;

    private final AccountSocket accountSocket;

    public CreateAccountMenu() {
        super(false, true);
        Gdx.input.setInputProcessor(stage);

        accountSocket = connect();

        skin = JellShock.getInstance().getAssetManager().get(Constants.NEON_SKIN_PATH);
        Table table = new Table(skin);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        table.setSize(width * 0.3f, height * 0.7f);
        table.setPosition((width - table.getWidth()) / 2, (height - table.getHeight()) / 2);
        table.setTransform(true);
        table.setOrigin(table.getWidth() / 2, table.getHeight() / 2);

        Label title = new Label("Create Account / Login", skin);
        Label userLabel = new Label("Username: ", skin);
        TextField userField = new TextField("", skin);
        Label passwordLabel = new Label("Password: ", skin);
        TextField.TextFieldStyle passwordStyle = skin.get("password", TextField.TextFieldStyle.class);
        TextField passwordField = new TextField("", passwordStyle);
        table.add(title).colspan(2).fillX().row();
        table.add(userLabel).pad(10);
        table.add(userField).row();
        table.add(passwordLabel).pad(10);
        table.add(passwordField).row();

        TextButton createButton = new TextButton("Create Account / Login", skin);
        TextButton backMenuButton = new TextButton("Back to Menu", skin);
        table.add(createButton);
        table.add(backMenuButton);

        createButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (userField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    DialogUtils.error("Username / password can't be empty", stage, skin);
                    return;
                }
                if (passwordField.getText().length() > 20) {
                    DialogUtils.error("Password can't be longer than 20 characters", stage, skin);
                    return;
                }
                name = userField.getText();
                password = passwordField.getText();

                accountSocket.emitAccountData(name, password);
            }
        });

        backMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                JellShock.getInstance().setScreen(MenuScreen.class);
            }
        });

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
    }

    @Override
    public void update(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        accountSocket.close();
        super.hide();
    }

    public AccountSocket connect() {
        if (accountSocket != null && !accountSocket.isConnected()) new Thread(accountSocket::connect).start();
        if (accountSocket != null) return accountSocket;
        AccountSocket socket = new AccountSocket(URI.create(Constants.SERVER_URL), IO.Options.builder().build(), this);
        new Thread(socket::connect).start();
        return socket;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void dispose() {
        accountSocket.close();
        super.dispose();
    }
}
