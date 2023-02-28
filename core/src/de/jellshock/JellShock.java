package de.jellshock;

import com.badlogic.gdx.Game;
import de.jellshock.game.screens.TestScreen;

public class JellShock extends Game {

	private static JellShock instance;
	private TestScreen testScreen;

	public JellShock() {
		instance = this;
	}

	@Override
	public void create() {
		testScreen = new TestScreen();

		setScreen(testScreen);
	}

	public TestScreen getTestScreen() {
		return testScreen;
	}

	public static JellShock getInstance() {
		return instance;
	}
}
