package de.jellshock.network.lobby;

import com.badlogic.gdx.Gdx;
import de.jellshock.JellShock;
import de.jellshock.game.screen.menu.CreateAccountMenu;
import de.jellshock.game.screen.menu.ServerSelectMenu;
import de.jellshock.game.util.DialogUtils;
import de.jellshock.network.AbstractSocket;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;

import java.net.URI;

public class AccountSocket extends AbstractSocket {

    private final CreateAccountMenu accountMenu;

    public AccountSocket(URI uri, IO.Options options, CreateAccountMenu accountMenu) {
        super(uri, options, "/account");
        this.accountMenu = accountMenu;
    }

    @Override
    public void onConnection(Socket socket) {
        if (isPresent()) emitAccountData(accountMenu.getName(), accountMenu.getPassword());
    }

    public boolean isPresent() {
        return accountMenu.getName() != null && accountMenu.getPassword() != null;
    }

    public void emitAccountData(String name, String password) {
        socket.emit("data", name, password, (Ack) args -> {
            String status = ((JSONObject) args[0]).getString("status");
            if (status.equals("ok") || status.equals("nok")) {
                Gdx.app.postRunnable(() -> {
                    JellShock.getInstance().setScreen(ServerSelectMenu.class);
                });
            } else {
                Gdx.app.postRunnable(() -> {
                    DialogUtils.error("Error while creating account", accountMenu.getStage(), accountMenu.getSkin());
                });
            }
        });
    }
}
