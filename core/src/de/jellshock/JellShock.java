package de.jellshock;

import com.badlogic.gdx.Game;
import de.jellshock.game.screens.TestScreen;

public class JellShock extends Game {

	@Override
	public void create() {
		setScreen(new TestScreen());
	}
}
