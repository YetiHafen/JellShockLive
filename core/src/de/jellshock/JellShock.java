package de.jellshock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import de.jellshock.game.screen.menu.MenuScreen;
import de.jellshock.game.weapon.WeaponManager;
import de.jellshock.network.SocketConnection;
import lombok.Getter;

@Getter
public class JellShock extends Game {

	private static JellShock instance;

	private AssetManager assetManager;
	private WeaponManager weaponManager;

	private MenuScreen menuScreen;

	public JellShock() {
		instance = this;
	}

	@Override
	public void create() {
		assetManager = new AssetManager();
		weaponManager = new WeaponManager();

		menuScreen = new MenuScreen();
		setScreen(menuScreen);

		SocketConnection socketConnection = new SocketConnection("localhost", 3000);
		socketConnection.init();
	}

	@Override
	public void dispose() {

	}

	public static JellShock getInstance() {
		return instance;
	}
}
