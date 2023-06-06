package de.jellshock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import de.jellshock.game.screen.AbstractScreen;
import de.jellshock.game.screen.game.offline.OfflineScreen;
import de.jellshock.game.screen.game.online.OnlineScreen;
import de.jellshock.game.screen.menu.*;
import de.jellshock.game.weapon.WeaponManager;
import de.jellshock.user.User;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Getter
public class JellShock extends Game {

	private static JellShock instance;

	private AssetManager assetManager;
	private WeaponManager weaponManager;

	private final Map<Class<? extends Screen>, Screen> screens;

	private User user;

	public JellShock() {
		instance = this;
		screens = new HashMap<>();
	}

	@Override
	public void create() {
		assetManager = new AssetManager();
		weaponManager = new WeaponManager();

		setScreen(MenuScreen.class);
	}

	public <T extends Screen> T getScreen(Class<T> screenClass) {
		Screen screen = screens.get(screenClass);
		if(screen != null) {
			return screenClass.cast(screen);
		}

		try {
			screen = screenClass.getDeclaredConstructor().newInstance();
			screens.put(screenClass, screen);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return screenClass.cast(screen);
	}

	public <T extends Screen> T setScreen(Class<T> screenClass) {
		T screen = getScreen(screenClass);
		setScreen(screen);
		return screen;
	}


	@Override
	public void dispose() {
		assetManager.dispose();
		screens.values().stream()
				.filter(Objects::nonNull)
				.forEach(Screen::dispose);
	}

	public static JellShock getInstance() {
		return instance;
	}
}
