package de.jellshock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.jellshock.game.screen.TestScreen;
import de.jellshock.game.screen.menu.MenuScreen;

public class JellShock extends Game {

	private static JellShock instance;

	private AssetManager assetManager;

	private TestScreen testScreen;
	private MenuScreen menuScreen;

	public JellShock() {
		instance = this;
	}

	@Override
	public void create() {
		assetManager = new AssetManager();

		testScreen = new TestScreen();
		menuScreen = new MenuScreen();
		setScreen(menuScreen);
	}

	@Override
	public void dispose() {

	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public TestScreen getTestScreen() {
		return testScreen;
	}

	public static JellShock getInstance() {
		return instance;
	}
}
